package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Solution;
import com.farhan.skripsibe.repository.SolutionRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/solutions")
@RequiredArgsConstructor
public class SolutionController {
	private final SolutionRepository solutionRepository;

	@DeleteMapping("{id}")
	public ResponseEntity<Object> getSymtoms(@PathVariable Long id) {
		// <Symptom> symptoms = dieseService.getSymtoms(code);
		Solution solution = solutionRepository.findById(id).get();
		solutionRepository.delete(solution);
		Map<String, String> response = new HashMap<>();
		response.put("status", "deleted");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
