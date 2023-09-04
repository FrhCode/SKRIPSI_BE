package com.farhan.skripsibe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.farhan.skripsibe.SkripsiBeApplication;
import com.farhan.skripsibe.model.Symptom;

@SpringBootTest(classes = SkripsiBeApplication.class)
public class SystomRepositoryTest {
	@Autowired
	private SymtomRepository systomRepository;

	@Test
	public void findByCode() {
		Symptom symtom = systomRepository.findByCode("KG01").orElseThrow();

		assertEquals("Borok pada kulit", symtom.getName());
	}
}
