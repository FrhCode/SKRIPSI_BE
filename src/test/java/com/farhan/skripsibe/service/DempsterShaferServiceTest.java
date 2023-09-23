package com.farhan.skripsibe.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.farhan.skripsibe.model.Symptom;

@SpringBootTest
public class DempsterShaferServiceTest {

	@Autowired
	private DempsterShaferService dempsterShaferService;

	@Autowired
	private SymtomService symtomService;

	@Test
	@Transactional
	void case1() {
		Symptom KG01 = symtomService.findByCode("KG01").get();
		Symptom KG02 = symtomService.findByCode("KG02").get();
		Symptom KG08 = symtomService.findByCode("KG08").get();
		Symptom KG07 = symtomService.findByCode("KG07").get();
		List<Symptom> symptoms = List.of(KG01, KG02, KG07, KG08);

		dempsterShaferService.calculate(symptoms);
	}
}
