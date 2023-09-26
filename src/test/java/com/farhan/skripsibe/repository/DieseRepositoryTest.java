package com.farhan.skripsibe.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.farhan.skripsibe.model.Diese;

@ActiveProfiles("test-case-1")
@SpringBootTest
public class DieseRepositoryTest {
	@Autowired
	private DieseRepository dieseRepository;

	@Test
	@Transactional
	void testFindBySymptomCode() {
		List<Diese> dieses = dieseRepository.findBySymptomCode("KG03");
		// List<Diese> dieses = dieseRepository.findBySymptomCode();
		System.out.println(dieses);
	}
}
