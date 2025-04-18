package com.example.job_application_eval.config;

public class PublicRouteChecker {
    public static boolean isPublic(String path) {
        return path.startsWith("/auth") ||
                path.startsWith("/jobPosting/all") ||
                path.startsWith("/jobPosting/getJobPosting") ||
                path.startsWith("/jobPosting/searchByJobTitle");
    }
}