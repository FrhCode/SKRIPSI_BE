package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.request.PaginateDieseRequest;
import com.farhan.skripsibe.service.DieseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/dieses")
@RequiredArgsConstructor
public class DieseController {
	private final DieseService dieseService;

	@GetMapping("count")
	public Map<String, Object> count() {
		Map<String, Object> response = new HashMap<>();
		long symtomCount = dieseService.count();
		response.put("diese_count", symtomCount);

		return response;
	}

	@GetMapping
	public Page<Diese> paginate(PaginateDieseRequest paginateDieseRequest) {
		return dieseService.paginate(paginateDieseRequest);
	}

	@GetMapping("{code}/symptoms")
	public Map<String, Object> getSymtoms(@PathVariable String code) {
		List<Symptom> symptoms = dieseService.getSymtoms(code);
		Map<String, Object> response = new HashMap<>();
		response.put("data", symptoms);
		return response;
	}
}
