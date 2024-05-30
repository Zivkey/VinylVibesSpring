package com.example.vinyl.vibes.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("REQUEST :: {}, METHOD :: {}", request.getRequestURI(), request.getMethod());
        if (request.getRequestURI().contains("/public")) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        return true;
    }
}
