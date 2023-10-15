package com.farhan.skripsibe.validator;

import java.util.List;

import com.farhan.skripsibe.annotation.ValidSymptoms;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidSymptomsValidator implements ConstraintValidator<ValidSymptoms, List<String>> {

	@Override
	public void initialize(ValidSymptoms constraintAnnotation) {
		// This method is usually empty, but you can use it to initialize your
		// validator.
	}

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		// Define your validation logic here.
		// Return true if the validation passes, false otherwise.
		// Example: return value != null && value.startsWith("abc");
		return true;
	}
}
