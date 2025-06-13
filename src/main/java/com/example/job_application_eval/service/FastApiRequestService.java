package com.example.job_application_eval.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface FastApiRequestService {

     <T> ResponseEntity<T> sendRequest(String url, HttpMethod httpMethod, Object payload, Class<T> responseType);
}
