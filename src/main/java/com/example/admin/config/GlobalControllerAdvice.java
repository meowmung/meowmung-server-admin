//package com.example.admin.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//
//    @Value("${custom.monitoring-url}")
//    private String monitoringUrl;
//
//    @ModelAttribute("monitoringUrl")
//    public String addMonitoringUrl() {
//        return "redirect: "+monitoringUrl;
//    }
//}
