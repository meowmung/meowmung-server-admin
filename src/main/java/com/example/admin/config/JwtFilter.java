//package com.example.admin.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//@RequiredArgsConstructor
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtUtils jwtUtils;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7); // "Bearer " 제거
//            if (jwtUtils.isValidToken(token)) {
//                Map<String, Object> stringObjectMap = jwtUtils.parseToken(token);
//                String role = stringObjectMap.get("role").toString();
//                if ("ADMIN".equals(role)) {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            }
//        }
//        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//    }
//}