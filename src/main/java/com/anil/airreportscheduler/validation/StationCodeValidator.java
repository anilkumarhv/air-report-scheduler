package com.anil.airreportscheduler.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StationCodeValidator implements ConstraintValidator<ValidStationCode, String> {

    private static final String STATION_CODE_PATTERN = "^[A-Z]{3,4}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches(STATION_CODE_PATTERN);
    }
}
