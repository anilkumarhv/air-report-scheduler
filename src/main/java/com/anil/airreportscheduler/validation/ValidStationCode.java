package com.anil.airreportscheduler.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StationCodeValidator.class)
@Documented
public @interface ValidStationCode {
    String message() default "Invalid station code. Must be 3-4 uppercase letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
