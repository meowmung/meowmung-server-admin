//package com.example.admin.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private final JwtFilter jwtFilter;
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // CSRF와 formLogin을 비활성화
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        http.authorizeHttpRequests(
//                request -> request.requestMatchers(
//                "/admin/**").hasRole("ADMIN") // ADMIN 권한 필요
//                .anyRequest().permitAll()
//                );
//               http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}