package com.example.job_application_eval.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteRoleWhitelist {

    public static final Map<String, List<String>> WHITELIST = new HashMap<>();

    static {

        // User routes
        WHITELIST.put("GET:/user/currentUser", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("GET:/user/listUsers", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("PATCH:/user/changePassw", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("PATCH:/user", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("DELETE:/user", List.of("ADMIN", "RECRUITER", "USER"));
        WHITELIST.put("DELETE:/user/{userId}", List.of("ADMIN"));
        WHITELIST.put("GET:/user/searchUserFullName", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("POST:/user/create", List.of("ADMIN"));
        WHITELIST.put("GET:/user/getUser", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("GET:/user/privilegedUsers", List.of("ADMIN"));

        // EducationController routes
        WHITELIST.put("DELETE:/education", List.of("USER"));
        WHITELIST.put("GET:/education/userEducations", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("GET:/education/getEducation", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("POST:/education/create", List.of("USER"));
        WHITELIST.put("PUT:/education/edit", List.of("USER"));

        // SkillController routes
        WHITELIST.put("DELETE:/skill", List.of("USER"));
        WHITELIST.put("GET:/skill/userSkills", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("GET:/skill/getSkill", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("POST:/skill/create", List.of("USER"));
        WHITELIST.put("PUT:/skill/edit", List.of("USER"));

        // WorkExperienceController routes
        WHITELIST.put("DELETE:/workExp", List.of("USER"));
        WHITELIST.put("GET:/workExp/userWorkExperiences", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("GET:/workExp/getWorkExperience", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("POST:/workExp/create", List.of("USER"));
        WHITELIST.put("PUT:/workExp/edit", List.of("USER"));


        // ProjectController routes
        WHITELIST.put("DELETE:/project", List.of("USER"));
        WHITELIST.put("GET:/project/userProjects", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("GET:/project/getProject", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("POST:/project/create", List.of("USER"));
        WHITELIST.put("PUT:/project/edit", List.of("USER"));

        // ProjectController routes
        WHITELIST.put("DELETE:/applicantEnglishLevel", List.of("USER"));
        WHITELIST.put("GET:/applicantEnglishLevel/getApplicantEnglishLevel", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("POST:/applicantEnglishLevel/create", List.of("USER"));
        WHITELIST.put("PUT:/applicantEnglishLevel/edit", List.of("USER"));

        // JobPostingController routes

        WHITELIST.put("POST:/jobPosting/create", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("PUT:/jobPosting/edit", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("DELETE:/jobPosting", List.of("ADMIN", "RECRUITER"));


        // JobApplicationController routes
        WHITELIST.put("POST:/jobApplication/apply", List.of("USER"));
        WHITELIST.put("GET:/jobApplication/anyApplicationFilter", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("GET:/jobApplication/myApplicationFilter", List.of("USER"));
        WHITELIST.put("PATCH:/jobApplication/changeStatus", List.of("ADMIN", "RECRUITER"));
        
    }
}
