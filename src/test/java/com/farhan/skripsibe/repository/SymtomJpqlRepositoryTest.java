package com.farhan.skripsibe.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.farhan.skripsibe.SkripsiBeApplication;

@SpringBootTest(classes = SkripsiBeApplication.class)
public class SymtomJpqlRepositoryTest {
	@Autowired
	private SymtomJpqlRepository symtomJpqlRepository;

}
