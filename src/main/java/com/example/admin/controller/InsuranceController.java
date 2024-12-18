package com.example.admin.controller;

import com.example.admin.dto.InsuranceCountDTO;
import com.example.admin.dto.TermCountDTO;
import com.example.admin.service.InsuranceService;
import com.example.admin.service.PdfService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class InsuranceController {

    @Value("${spring.admin.monitoring-url}")
    private String monitoringUrl;

    private final InsuranceService insuranceService;
    private final PdfService pdfService;
    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/insurance")
    public String insurance(Model model) {
        List<InsuranceCountDTO> insuranceStats = insuranceService.findInsuranceCompany();
        model.addAttribute("insuranceStats", insuranceStats);
        // 합계 계산
        Long totalCount = insuranceStats.stream()
                .mapToLong(InsuranceCountDTO::getCount)
                .sum();

        // 모델에 데이터 추가
        model.addAttribute("insuranceStats", insuranceStats);
        model.addAttribute("totalCount", totalCount);
        return "insurance";
    }

    @GetMapping("/terms")
    public String getAllTerms(@RequestParam(name = "insuranceId", required = false) String insuranceId, Model model) {
        List<InsuranceCountDTO> insuranceStats = insuranceService.findInsuranceCompany();
        model.addAttribute("insuranceStats", insuranceStats);

        List<TermCountDTO> termStats = insuranceService.getAllTermsGroupedByResultsId(insuranceId);
        for(TermCountDTO term : termStats) {
            System.out.println(term.getTermId());
        }
        model.addAttribute("termStats", termStats);

        return "terms";
    }

    // 업로드 폴더 생성
    static {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @GetMapping("/pdf")
    public String pdf(Model model) {
         return "pdf";
    }

    @GetMapping("/monitoring")
    public String monitoring(Model model) {
        return "redirect:" + monitoringUrl ;
    }

    @PostMapping("/upload")
    public String uploadFile (@RequestParam("file") MultipartFile file
                            , @RequestParam("terms") String termsJson) throws IOException {
        // pdf 파일 처리
        if (file.isEmpty()) {
            return "파일이 업로드되지 않았습니다.";
        }
        String key = "data/pdf/" + file.getOriginalFilename();

        // termsJson을 List<Map<String, Object>> 형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> terms = objectMapper.readValue(termsJson, new TypeReference<List<Map<String, Object>>>() {});

        // s3에 업로드
        pdfService.upload(file);
        pdfService.uploadFileToS3(terms, key);

        return "pdf";
    }
}
