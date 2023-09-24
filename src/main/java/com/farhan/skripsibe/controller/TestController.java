package com.farhan.skripsibe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

	@GetMapping()
	public List<List<String>> getMethodName() {
		return List.of(List.of("Farhan", "Zydan"));
	}

}
