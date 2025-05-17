package com.example.job_application_eval.config;

public class PublicRouteChecker {
    public static boolean isPublic(String path) {
        return path.startsWith("/auth") ||
                path.startsWith("/jobPosting/all") ||
                path.startsWith("/jobPosting/getJobPosting") ||
                path.startsWith("/jobPosting/searchByJobTitle") ||
                path.startsWith("/swagger-ui") ||              // ✅ Fix
                path.startsWith("/swagger-ui.html") ||
                path.startsWith("/v3/api-docs") ||             // ✅ Covers all /v3/api-docs/*
                path.startsWith("/v3/api-docs.yaml") ||        // ✅ Fix broken line
                path.startsWith("/webjars");
    }
}