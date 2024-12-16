package com.example.admin.controller;

import com.example.admin.dto.InsuranceCountDTO;
import com.example.admin.dto.TermCountDTO;
import com.example.admin.repository.RecommendedTermsRepository;
import com.example.admin.service.InsuranceService;
import com.example.admin.service.PdfService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class InsuranceController {
    private final InsuranceService insuranceService;
    private final PdfService pdfService;
    private static final String UPLOAD_DIR = "uploads/";
    private final RecommendedTermsRepository recommendedTermsRepository;

    @GetMapping("/insurance")
    public String insurance(Model model) {
        List<InsuranceCountDTO> insuranceStats = insuranceService.findInsuranceCompany();
        model.addAttribute("insuranceStats", insuranceStats);
        // 합계 계산
        Long totalCount = insuranceStats.stream()
                .mapToLong(InsuranceCountDTO::getCount)
                .sum();

        // 특정 RecommendedResults의 특약 데이터
        Long resultsId = 1L; // 조회할 RecommendedResults ID (예: 기본값)
//        List<TermCountDTO> termStats = insuranceService.getTopRecommendedTerms(resultsId);
//        model.addAttribute("termStats", termStats);

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
        // 모든 RecommendedResults와 관련된 termId 데이터 가져오기
//        List<String> insuranceIds = new ArrayList<>();
//        List<InsuranceCountDTO> insuranceStats = insuranceService.findInsuranceCompany();
//        for (InsuranceCountDTO insuranceCountDTO : insuranceStats) {
//            insuranceIds.add(insuranceCountDTO.getInsuranceId());
//            System.out.println(insuranceCountDTO.getInsuranceId());
//        }
//        model.addAttribute("insuranceIds", insuranceIds);
//

        // 모든 RecommendedResults와 관련된 termId 데이터 가져오기
//        List<TermCountDTO> termStats = insuranceService.getTopRecommendedTerms();

//        List<TermCountDTO> termStats = insuranceService.getAllTermsGroupedByResultsId();
//        for (TermCountDTO termStat : termStats) {
//            System.out.println(termStat.getTermId());
//        }
//
//        model.addAttribute("termStats", termStats);
//
//        // 해당 결과에 대한 보험과 특약 데이터


//        insuranceService.getTopRecommendedTerms();
//        List<TermCountDTO> termStats = new ArrayList<>();
//        for (String insuranceId : insuranceIds) {
//
//            termStats = recommendedTermsRepository.findAllGroupedByResultsId();
//        }
//        model.addAttribute("termStats", termStats);

        // 해당 결과에 대한 보험과 특약 데이터
//        List<InsuranceCountDTO> insuranceStats = insuranceService.findInsuranceCompany();
//        model.addAttribute("insuranceStats", insuranceStats);


    }






//    @GetMapping("/terms")
//    public String getAllTerms(@RequestParam("resultsId") Long resultsId, Model model) {
//        // 모든 RecommendedResults와 관련된 termId 데이터 가져오기
//        List<TermCountDTO> termStats = insuranceService.getTopRecommendedTerms(resultsId);
//        model.addAttribute("termStats", termStats);
//
//        // 해당 결과에 대한 보험과 특약 데이터
//        List<InsuranceCountDTO> insuranceStats = insuranceService.findInsuranceCompany();
//        model.addAttribute("insuranceStats", insuranceStats);
//
//        return "terms";
//    }


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

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String key = "data/pdf/" + file.getOriginalFilename();
        String s = pdfService.upload(file);
        pdfService.sendData(key);
        return "pdf";
    }
}
