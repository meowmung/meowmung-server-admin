package com.example.admin.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafConfig {
    @GetMapping
    public String thymeleafConfig() {
        return "index";
    }
}