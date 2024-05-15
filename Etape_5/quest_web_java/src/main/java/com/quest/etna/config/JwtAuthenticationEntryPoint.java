package com.quest.etna.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.quest.etna.DTO.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ErrorResponseDTO<String> errorResponse = new ErrorResponseDTO<>(
            "Accès non autorisé ou non connecté.", 
            401, 
            HttpStatus.UNAUTHORIZED.getReasonPhrase(), 
            request.getRequestURI()
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // Set character encoding to UTF-8
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse)); // Use getWriter().write() instead of getOutputStream().println()
    }
}