package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.service.FastApiRequestService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class FastApiRequestServiceImpl implements FastApiRequestService {

    private final RestTemplate restTemplate;

    public ResponseEntity<String> sendRequest(String url, HttpMethod httpMethod, Object payload) {
        try {
            HttpEntity<Object> requestEntity;
            if (payload != null) {
                requestEntity = new HttpEntity<>(payload);
            } else {
                requestEntity = new HttpEntity<>(null);
            }

            ResponseEntity<String> response = restTemplate.exchange(
                    url, httpMethod, requestEntity, String.class);

            System.out.println("Response: " + response.getBody());

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to process request: HTTP status " + response.getStatusCode());
            }
            return response;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while processing request: " + e.getMessage(), e);
        }
    }
}
