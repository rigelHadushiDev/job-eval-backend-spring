package com.example.job_application_eval.config.utils;

import com.example.job_application_eval.entities.enums.ProficiencyLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProficiencyLevelConverter implements AttributeConverter<ProficiencyLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProficiencyLevel level) {
        return level != null ? level.getCode() : null;
    }

    @Override
    public ProficiencyLevel convertToEntityAttribute(Integer dbData) {
        return dbData != null ? ProficiencyLevel.fromCode(dbData) : null;
    }
}
