package com.example.admin.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.admin.dto.SendDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.http.ResponseEntity;
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

    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucket;

    @Value("${spring.admin.ml-server}")
    private String mlServer;

    public ResponseEntity<String> uploadFileToS3(List<Map<String, Object>> terms, String key) {
        SendDTO sendDTO = SendDTO.builder()
                .terms(terms)
                .file_path(key)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SendDTO> request = new HttpEntity<>(sendDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(mlServer, HttpMethod.POST, request, String.class);
        return response;
    }

    // s3업로드

    public String upload(MultipartFile file) throws IOException {
        File uploadFile = convert(file)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        String fileName = uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        removeNewFile(uploadFile);
        return uploadImageUrl;
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
