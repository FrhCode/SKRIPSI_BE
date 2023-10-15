package com.farhan.skripsibe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.farhan.skripsibe.model.Symptom;

@SpringBootTest
public class SymtomRepositoryTest {
	@Autowired
	private SymtomRepository symtomRepository;

	@Test
	@Transactional
	void testFindByCodesIn() {
		List<String> symptoms = List.of("SC01", "SC02");
		List<Symptom> result = symtomRepository.findByCodesIn(symptoms);

		assertEquals(symptoms.size(), result.size());
	}

	@Test
	@Transactional
	void testFindByCodesInWhenOneOfTheCodeIsNotExist() {
		List<String> symptoms = List.of("SC01", "SC02", "SC00");
		List<Symptom> result = symtomRepository.findByCodesIn(symptoms);

		assertEquals(symptoms.size() - 1, result.size());
	}
}
