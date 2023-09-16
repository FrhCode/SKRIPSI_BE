package com.farhan.skripsibe.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomCriteriaRepository;
import com.farhan.skripsibe.repository.SymtomJpqlRepository;
import com.farhan.skripsibe.request.SymtomPaginateRequest;
import com.farhan.skripsibe.request.TestSymtomSearchRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/symptoms")
@RequiredArgsConstructor
public class SymtomController {
	private final SymtomCriteriaRepository symtomCriteriaRepository;

	@GetMapping("all")
	public List<Symptom> findAll() {
		return symtomCriteriaRepository.findAll();
	}

	@GetMapping("search")
	public List<Symptom> search(TestSymtomSearchRequest request) {
		return symtomCriteriaRepository.search(request);
	}

	@GetMapping
	public Page<Symptom> paginate(SymtomPaginateRequest symtomPaginateRequest) {
		return symtomCriteriaRepository.paginate(symtomPaginateRequest);
	}
}
