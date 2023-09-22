package com.farhan.skripsibe.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.farhan.skripsibe.model.Diese;

@SpringBootTest
// @TestPropertySource(locations = "classpath:application-test.properties")
public class DempsterShaferServiceTest {
	@Autowired
	private DieseService dieseService;

	@Test
	void method1() {
		Optional<Diese> diesesOptional = dieseService.findByCode("P1");
		System.out.println(diesesOptional.isPresent());
	}
}
