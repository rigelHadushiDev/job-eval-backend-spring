package com.example.job_application_eval.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ProficiencyLevel {
    ELEMENTARY_PROFICIENCY("Elementary proficiency"),
    LIMITED_WORKING_PROFICIENCY("Limited working proficiency"),
    PROFESSIONAL_WORKING_PROFICIENCY("Professional working proficiency"),
    FULL_PROFESSIONAL_PROFICIENCY("Full professional proficiency"),
    NATIVE_OR_BILINGUAL_PROFICIENCY("Native or bilingual proficiency");

    private final String label;

    ProficiencyLevel(String label) {
        this.label = label;
    }

    @JsonValue
    public String toValue() {
        return label;
    }

    @JsonCreator
    public static ProficiencyLevel fromValue(String value) {
        for (ProficiencyLevel level : ProficiencyLevel.values()) {
            if (level.label.equalsIgnoreCase(value.trim())) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown proficiency level: " + value);
    }
}
