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
        WHITELIST.put("DELETE:/applicantLanguage", List.of("USER"));
        WHITELIST.put("GET:/applicantLanguage/getApplicantLanguages", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("GET:/applicantLanguage/getApplicantLanguage", List.of("USER", "RECRUITER", "ADMIN"));
        WHITELIST.put("POST:/applicantLanguage/create", List.of("USER"));
        WHITELIST.put("PUT:/applicantLanguage/edit", List.of("USER"));

    }
}
