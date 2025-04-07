package com.example.job_application_eval.config.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


import java.nio.file.AccessDeniedException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        HttpStatusCode statusCode = ex.getStatusCode();
        HttpStatus status = HttpStatus.resolve(statusCode.value());

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", ZonedDateTime.now());
        errorBody.put("status", statusCode.value());
        errorBody.put("error", (status != null) ? status.getReasonPhrase() : "Unknown Error");  // FIXED
        errorBody.put("path", request.getRequestURI());
        errorBody.put("message", ex.getReason());

        return new ResponseEntity<>(errorBody, statusCode);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", ZonedDateTime.now());
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("path", request.getRequestURI());
        errorBody.put("message", "Access denied");

        return new ResponseEntity<>(errorBody, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", ZonedDateTime.now());
        errorBody.put("status", status.value());
        errorBody.put("error", status.getReasonPhrase());
        errorBody.put("path", request.getRequestURI());
        errorBody.put("message", "Unexpected error occurred.");


        ex.printStackTrace();

        return new ResponseEntity<>(errorBody, status);
    }
}