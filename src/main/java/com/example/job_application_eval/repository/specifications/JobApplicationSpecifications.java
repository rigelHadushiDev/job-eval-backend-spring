package com.example.job_application_eval.repository.specifications;

import com.example.job_application_eval.entities.JobApplicationEntity;
import com.example.job_application_eval.entities.enums.ApplicationStatus;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
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

    public static Specification<JobApplicationEntity> hasFullName(String fullName) {
        return (root, query, criteriaBuilder) -> {
            if (fullName != null && !fullName.isEmpty()) {
                Join<Object, Object> userJoin = root.join("user");
                return criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("fullName")), "%" + fullName.toLowerCase() + "%");
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> hasJobTitle(String jobTitle) {
        return (root, query, criteriaBuilder) -> {
            if (jobTitle != null && !jobTitle.isEmpty()) {
                Join<Object, Object> jobPostingJoin = root.join("jobPosting");
                return criteriaBuilder.like(criteriaBuilder.lower(jobPostingJoin.get("jobTitle")), "%" + jobTitle.toLowerCase() + "%");
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> isJobPostingClosed(Boolean closed) {
        return (root, query, criteriaBuilder) -> {
            if (closed != null) {
                Join<Object, Object> jobPostingJoin = root.join("jobPosting");
                return criteriaBuilder.equal(jobPostingJoin.get("closed"), closed);
            }
            return null;
        };
    }

    public static Specification<JobApplicationEntity> hasJobApplicationId(Long jobApplicationId) {
        return (root, query, criteriaBuilder) -> {
            if (jobApplicationId != null) {
                return criteriaBuilder.equal(root.get("jobApplicationId"), jobApplicationId);
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
                                                                         String sortBy, String orderType, String fullName, String jobTitle,
                                                                         Boolean closed, Long jobApplicationId) {

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
        if (fullName != null && !fullName.isEmpty()) {
            spec = spec.and(hasFullName(fullName));
        }
        if (jobTitle != null && !jobTitle.isEmpty()) {
            spec = spec.and(hasJobTitle(jobTitle));
        }
        if (closed != null) {
            spec = spec.and(isJobPostingClosed(closed));
        }
        if (jobApplicationId != null) {
            spec = spec.and(hasJobApplicationId(jobApplicationId));
        }
        spec = spec.and(orderBy(sortBy, orderType));

        return spec;
    }
}