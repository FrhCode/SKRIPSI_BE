package com.farhan.skripsibe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.farhan.skripsibe.SkripsiBeApplication;
import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.service.ConsultationService;

@SpringBootTest(classes = SkripsiBeApplication.class)
public class ConsultationRepositoryTest {
	@Autowired
	ConsultationService consultationService;

}
