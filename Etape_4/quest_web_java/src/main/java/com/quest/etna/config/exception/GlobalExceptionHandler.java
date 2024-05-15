package com.quest.etna.config.exception;

import com.quest.etna.DTO.ErrorResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleNotFoundException(NotFoundException ex) {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        String url = sra.getRequest().getRequestURL().toString();
        
        return new ResponseEntity<>(
            new ErrorResponseDTO<String>(
                ex.getMessage(),
                404,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                url),
            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoRightsException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleNoRightsException(NoRightsException ex) {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        String url = sra.getRequest().getRequestURL().toString();
        return new ResponseEntity<>(
            new ErrorResponseDTO<String>(
                ex.getMessage(),
                403,
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                url),
            HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<ErrorResponseDTO<String>> handleNoAccessException(NoAccessException ex) {
        ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        String url = sra.getRequest().getRequestURL().toString();
        return new ResponseEntity<>(
            new ErrorResponseDTO<String>(
                ex.getMessage(),
                401,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                url),
            HttpStatus.UNAUTHORIZED);
    }



}