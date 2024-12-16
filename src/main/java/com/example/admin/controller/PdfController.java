//package com.example.admin.controller;
//
//import com.example.admin.service.PdfService;
//import java.net.URLEncoder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Controller
//@RequestMapping("/admin/pdf")
//@RequiredArgsConstructor
//public class PdfController {
//
//    private static final String UPLOAD_DIR = "uploads/";
//    private static PdfService pdfService;
//
//    // 업로드 폴더 생성
//    static {
//        File uploadDir = new File(UPLOAD_DIR);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdirs();
//        }
//    }
//
//    @GetMapping("/pdf")
//    public String pdf(Model model) {
//        return "pdf";
//    }
//
//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
//        String key = "data/pdf/" + file.getOriginalFilename();
//        String s = pdfService.upload(file);
//        pdfService.sendData(key);
//        return "";
//    }
//}