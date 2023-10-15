package com.farhan.skripsibe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.farhan.skripsibe.validator.ValidSymptomsValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSymptomsValidator.class)
public @interface ValidSymptoms {
	String message() default "Invalid symptoms code";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
