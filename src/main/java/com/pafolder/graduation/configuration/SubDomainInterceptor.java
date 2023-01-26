package com.pafolder.graduation.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class SubDomainInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Logger log = LoggerFactory.getLogger(getClass());
        log.error(request.getRequestURL().toString());
        if (request.getRequestURL().toString().toLowerCase().contains("cv.pafolder")) {
            response.sendRedirect("https://pafolder.github.io");
        }
        return true;
    }
}
