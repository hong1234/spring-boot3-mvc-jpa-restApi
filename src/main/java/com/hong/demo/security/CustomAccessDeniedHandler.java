
package com.hong.demo.security;

// import jakarta.servlet.*;
// import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Component // ("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Customize the response when access is denied
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Customer Access Denied. You do not have sufficient privileges to access this resource.");
    }
}