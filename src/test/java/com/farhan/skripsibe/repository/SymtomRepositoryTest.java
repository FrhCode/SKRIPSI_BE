package com.farhan.skripsibe.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.farhan.skripsibe.model.Diese;

@SpringBootTest
public class SymtomRepositoryTest {
	@Autowired
	private SymtomRepository symtomRepository;

	@Test
	@Transactional
	void testFindByCodesIn() {
		List.of("");
		symtomRepository.findByCodesIn(null);
	}
}
