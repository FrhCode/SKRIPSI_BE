package com.farhan.skripsibe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.farhan.skripsibe.model.Symptom;

import lombok.Data;

@SpringBootTest
public class Playgound {
	@Autowired
	private SymtomService symtomService;

	@Test
	@Transactional
	void getDiesesFromSymptom() {
		Symptom symptom = symtomService.findByCode("KG01").get();

		System.out.println(symptom.getDieses());
	}

	@Test
	void pg2() {
		Students students = new Students();
		students.setNames(List.of());
		if (students.getNames() == null) {
			System.out.println("Empty");
		} else
			System.out.println("OK");
	}

	@Data
	class Students {
		private List<String> names;
	}

	@Test
	void pg3() {
		List<String> names = List.of("farhan");
		System.out.println(names.isEmpty());
	}

	@Test
	void pg4() {
		Map<List<String>, String> studentMaps = new HashMap<>();

		studentMaps.put(List.of("Mohammad", "Farhan"), "Mohammad Farhan");

		System.out.println(studentMaps.containsKey(List.of("Mohammad", "Farhan")));

	}
}
