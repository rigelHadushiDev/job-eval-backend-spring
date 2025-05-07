package com.example.job_application_eval.repository.specifications;

import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class JobApplicationSpecifications {

    public static Specification<JobApplicationEntity> hasJobPostingId(Long jobPostingId) {
        return (root, query, criteriaBuilder) -> {
            if (jobPostingId != null) {
                return criteriaBuilder.equal(root.get("jobPosting").get("jobPostingId"), jobPostingId);
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> hasStatus(ApplicationStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status != null) {
                return criteriaBuilder.equal(root.get("status"), status);
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> hasApplicationDate(LocalDateTime applicationDate) {
        return (root, query, criteriaBuilder) -> {
            if (applicationDate != null) {
                return criteriaBuilder.equal(root.get("applicationDate"), applicationDate);
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId != null) {
                return criteriaBuilder.equal(root.get("user").get("userId"), userId);
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> orderBy(String sortBy, String orderType) {
        return (root, query, criteriaBuilder) -> {
            if ("generalScore".equalsIgnoreCase(sortBy)) {
                if ("asc".equalsIgnoreCase(orderType)) {
                    query.orderBy(criteriaBuilder.asc(root.get("generalScore")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("generalScore")));
                }
            } else if ("applicationDate".equalsIgnoreCase(sortBy)) {
                if ("asc".equalsIgnoreCase(orderType)) {
                    query.orderBy(criteriaBuilder.asc(root.get("applicationDate")));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get("applicationDate")));
                }
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<JobApplicationEntity> buildSpecification(Long userId, ApplicationStatus status,
                                                                         Long jobPostingId, LocalDateTime applicationDate,
                                                                         String sortBy, String orderType) {

        Specification<JobApplicationEntity> spec = Specification.where(null);

        if (userId != null) {
            spec = spec.and(hasUserId(userId));
        }
        if (status != null) {
            spec = spec.and(hasStatus(status));
        }
        if (jobPostingId != null) {
            spec = spec.and(hasJobPostingId(jobPostingId));
        }
        if (applicationDate != null) {
            spec = spec.and(hasApplicationDate(applicationDate));
        }

        spec = spec.and(orderBy(sortBy, orderType));

        return spec;
    }
}