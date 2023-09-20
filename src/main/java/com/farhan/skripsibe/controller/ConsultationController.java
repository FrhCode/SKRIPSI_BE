package com.farhan.skripsibe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.request.ConsultationPaginateRequest;
import com.farhan.skripsibe.response.GetAllResponse;
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

	@GetMapping("all")
	public GetAllResponse<Consultation> all(@RequestParam(required = false) Optional<Integer> size) {
		List<Consultation> consultations = new ArrayList<>();
		if (size.isPresent()) {
			consultations = consultationService.findAll(PageRequest.of(0, size.get()));
		} else {
			consultations = consultationService.findAll();
		}

		return new GetAllResponse<Consultation>(consultations);
	}

	@GetMapping
	public Page<Consultation> paginate(ConsultationPaginateRequest consultationPaginateRequest) {
		return consultationService.paginate(consultationPaginateRequest);
	}
}
