package com.example.job_application_eval.service.impl;

import com.example.job_application_eval.service.FastApiRequestService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FastApiRequestServiceImpl implements FastApiRequestService {

    private final RestTemplate restTemplate;

    @Override
    public <T> ResponseEntity<T> sendRequest(String url, HttpMethod httpMethod, Object payload, Class<T> responseType) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = (String) authentication.getCredentials();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<Object> requestEntity = new HttpEntity<>(payload, headers);
System.out.println("Sending payload to FastAPI: " + payload);
        return restTemplate.exchange(
                url, httpMethod, requestEntity, responseType
        );
    }
}
