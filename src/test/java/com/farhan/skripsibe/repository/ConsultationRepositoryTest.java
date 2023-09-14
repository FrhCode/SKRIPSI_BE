package com.farhan.skripsibe.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.farhan.skripsibe.SkripsiBeApplication;
import com.farhan.skripsibe.service.ConsultationService;

@SpringBootTest(classes = SkripsiBeApplication.class)
public class ConsultationRepositoryTest {
	@Autowired
	ConsultationService consultationService;

}