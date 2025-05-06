package com.example.job_application_eval.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ProficiencyLevel {
    NONE(0, "None"),
    A1(1, "A1"),
    A2(2, "A2"),
    B1(3, "B1"),
    B2(4, "B2"),
    C1(5, "C1"),
    C2(6, "C2");

    private final int code;
    private final String label;

    ProficiencyLevel(int code, String label) {
        this.code = code;
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

    public static ProficiencyLevel fromCode(int code) {
        for (ProficiencyLevel level : ProficiencyLevel.values()) {
            if (level.code == code) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown proficiency code: " + code);
    }
}
