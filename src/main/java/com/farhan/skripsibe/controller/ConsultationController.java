package com.farhan.skripsibe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.repository.ConsultationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {
	private final ConsultationRepository consultationRepository;

	@GetMapping("{invoiceNumber}")
	public Consultation findAll(@PathVariable String invoiceNumber) {
		return consultationRepository.findByinvoice(invoiceNumber);
	}
}
