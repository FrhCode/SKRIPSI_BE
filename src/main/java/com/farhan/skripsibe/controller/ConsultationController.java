package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.request.ConsultationPaginateRequest;
import com.farhan.skripsibe.service.ConsultationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {
	private final ConsultationService consultationService;

	@GetMapping("{invoiceNumber}")
	public Consultation find(@PathVariable String invoiceNumber) {
		return consultationService.findByinvoice(invoiceNumber);
	}

	@GetMapping("count")
	public Map<String, Object> count() {
		Map<String, Object> response = new HashMap<>();
		long symtomCount = consultationService.count();
		response.put("consultation_count", symtomCount);
		return response;
	}

	@GetMapping
	public Page<Consultation> paginate(ConsultationPaginateRequest consultationPaginateRequest) {
		return consultationService.paginate(consultationPaginateRequest);
	}
}
