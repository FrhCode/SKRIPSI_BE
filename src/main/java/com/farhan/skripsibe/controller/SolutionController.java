package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Solution;
import com.farhan.skripsibe.repository.SolutionRepository;
import com.farhan.skripsibe.request.EditSolutionsRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/solutions")
@RequiredArgsConstructor
public class SolutionController {
	private final SolutionRepository solutionRepository;

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteSolution(@PathVariable Long id) {
		Solution solution = solutionRepository.findById(id).get();
		solutionRepository.delete(solution);
		Map<String, String> response = new HashMap<>();
		response.put("status", "deleted");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> editSolution(@PathVariable Long id,
			@Valid @RequestBody EditSolutionsRequest editSolutionsRequest) {

		Solution solution = solutionRepository.findById(id).get();
		solution.setName(editSolutionsRequest.getName());
		solution.setDescription(editSolutionsRequest.getDescription());

		solutionRepository.save(solution);

		Map<String, String> response = new HashMap<>();
		response.put("status", "Success");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
