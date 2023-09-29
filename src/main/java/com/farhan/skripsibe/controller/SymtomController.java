package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.request.PaginateSymtomRequest;
import com.farhan.skripsibe.request.SymtomSearchRequest;
import com.farhan.skripsibe.response.BaseResponse;
import com.farhan.skripsibe.service.SymptomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/symptoms")
@RequiredArgsConstructor
public class SymtomController {
	private final SymptomService symtomService;

	@GetMapping("all")
	public BaseResponse<Symptom> findAll() {
		List<Symptom> symptoms = symtomService.findAll();
		return new BaseResponse<Symptom>(symptoms);
	}

	@GetMapping("search")
	public List<Symptom> search(SymtomSearchRequest request) {
		return symtomService.search(request);
	}

	@GetMapping("count")
	public Map<String, Object> count() {
		Map<String, Object> response = new HashMap<>();
		long symtomCount = symtomService.count();
		response.put("symptom_count", symtomCount);
		return response;
	}

	@GetMapping
	public Page<Symptom> paginate(PaginateSymtomRequest symtomPaginateRequest) {
		return symtomService.paginate(symtomPaginateRequest);
	}
}
