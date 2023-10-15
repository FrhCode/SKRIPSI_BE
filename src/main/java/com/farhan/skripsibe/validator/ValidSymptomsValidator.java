package com.farhan.skripsibe.validator;

import java.util.List;

import com.farhan.skripsibe.annotation.ValidSymptoms;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidSymptomsValidator implements ConstraintValidator<ValidSymptoms, List<String>> {
	private final SymtomRepository symtomRepository;

	@Override
	public boolean isValid(List<String> symptomsCode, ConstraintValidatorContext context) {
		List<Symptom> symptoms = symtomRepository.findByCodesIn(symptomsCode);
		boolean isAllSymptomsCodeValid = symptomsCode.size() == symptoms.size();
		return isAllSymptomsCodeValid;
	}
}
