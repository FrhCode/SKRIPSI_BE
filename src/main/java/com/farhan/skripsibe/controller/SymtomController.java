package com.farhan.skripsibe.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomJpqlRepository;
import com.farhan.skripsibe.request.SymtomPaginateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/symptom")
@RequiredArgsConstructor
public class SymtomController {
	private final SymtomJpqlRepository symtomJpqlRepository;

	@GetMapping
	public Page<Symptom> paginate(SymtomPaginateRequest symtomPaginateRequest) {
		return symtomJpqlRepository.paginate(symtomPaginateRequest);
	}
}
