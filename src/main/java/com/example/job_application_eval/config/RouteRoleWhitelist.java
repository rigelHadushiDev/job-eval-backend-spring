package com.example.job_application_eval.config;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteRoleWhitelist {

    public static final Map<String, List<String>> WHITELIST = new HashMap<>();

    static {
        WHITELIST.put("GET:/user/currentUser", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("GET:/user/listUsers", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("PATCH:/user/changePassw", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("PATCH:/user", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("DELETE:/user", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("DELETE:/user/{userId}", List.of("ADMIN"));
        WHITELIST.put("GET:/user/searchUserFullName", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("POST:/user/create", List.of("ADMIN"));
    }
}