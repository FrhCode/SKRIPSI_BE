package com.farhan.skripsibe.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomJpqlRepository;
import com.farhan.skripsibe.request.SymtomPaginateRequest;
import com.farhan.skripsibe.request.TestRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/symptom")
@RequiredArgsConstructor
public class SymtomController {
	private final SymtomJpqlRepository symtomJpqlRepository;

	@GetMapping("/testing")
	public TestRequest testing(TestRequest request) {
		return request;
		// return symtomJpqlRepository.paginate(symtomPaginateRequest);
	}

	@GetMapping
	public Page<Symptom> paginate(SymtomPaginateRequest symtomPaginateRequest) {
		return symtomJpqlRepository.paginate(symtomPaginateRequest);
	}
}
