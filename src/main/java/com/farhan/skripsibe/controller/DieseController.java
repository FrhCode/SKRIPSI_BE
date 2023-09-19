package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
