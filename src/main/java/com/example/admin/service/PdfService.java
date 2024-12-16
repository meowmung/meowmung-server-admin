package com.example.admin.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfService {
    private static final String UPLOAD_DIR = "uploads/";

    static {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }


    private final AmazonS3 amazonS3Client;
    private final RestTemplate restTemplate;

    @Value("${aws.s3.bucket-name}")
    private String bucket;

    public String sendData(String key){

        return "good";
    }
    public String uploadFileToS3(String key) {
        System.out.println(key);

        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> terms = new ArrayList<>();

        // Add terms to request body
        Map<String, Object> term1 = new HashMap<>();
        term1.put("page", 5);
        term1.put("term_name", "반려동물의료비");
        terms.add(term1);

        Map<String, Object> term2 = new HashMap<>();
        term2.put("page", 9);
        term2.put("term_name", "반려동물의료비확장보장(슬관절/고관절 탈구");
        terms.add(term2);

        requestBody.put("terms", terms);
        requestBody.put("file_path", key);

//        data/pdf/KB_dog.pdf"

        // Set up headers and entity for request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // Execute POST request using exchange
        String uri = "http://192.168.1.101:8008/insurance/admin";
        return restTemplate.exchange(uri, HttpMethod.POST, request, String.class).getBody();
    }
    private Optional<File> convert(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            log.error("File is empty");
            return Optional.empty();
        }

        String fileName = Objects.requireNonNullElse(file.getOriginalFilename(), "default-file");
        File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        } else {
            log.error("File creation failed at: {}", convertFile.getAbsolutePath());
            return Optional.empty();
        }
    }


    public String upload(MultipartFile file) throws IOException {
        File uploadFile = convert(file)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        System.out.println("upload service 메서드");
        String fileName = uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        System.out.println("-----------------------");
        System.out.println(uploadImageUrl);
        System.out.println("-----------------------");
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


}
