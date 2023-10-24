package com.farhan.skripsibe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Solution;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.repository.SolutionRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.request.AddSolutionsRequest;
import com.farhan.skripsibe.request.AddSymptomsRequest;
import com.farhan.skripsibe.request.CreateDieseRequest;
import com.farhan.skripsibe.request.EditDieseRequest;
import com.farhan.skripsibe.request.PaginateDieseRequest;
import com.farhan.skripsibe.response.BaseResponse;
import com.farhan.skripsibe.response.MessageResponse;
import com.farhan.skripsibe.service.DieseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/dieses")
@RequiredArgsConstructor
public class DieseController {
	private final DieseService dieseService;
	private final DieseRepository dieseRepository;
	private final SymtomRepository symtomRepository;
	private final SolutionRepository solutionRepository;

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
	public BaseResponse<Symptom> getSymtoms(@PathVariable String code) {
		Set<Symptom> symptoms = dieseService.getSymtoms(code);
		return new BaseResponse<Symptom>(new ArrayList<>(symptoms));
	}

	@GetMapping("{code}/solutions")
	public BaseResponse<Solution> getSolution(@PathVariable String code) {
		List<Solution> solutions = dieseRepository.findByCode(code).orElseThrow().getSolutions();
		return new BaseResponse<Solution>(solutions);
	}

	@PostMapping("{code}/symptoms")
	public ResponseEntity<Object> addSymtoms(@Valid @RequestBody AddSymptomsRequest addSymptomsRequest,
			@PathVariable String code) {
		List<String> symptomsCode = addSymptomsRequest.getSymptomsCode();

		Diese diese = dieseRepository.findByCode(code).orElseThrow();

		diese.addSymptom(symtomRepository.findByCode(symptomsCode.get(0)).get());

		dieseRepository.save(diese);

		Map<String, String> response = new HashMap<>();
		response.put("status", "created");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("{code}/solutions")
	public ResponseEntity<Object> addSolution(@Valid @RequestBody AddSolutionsRequest addSolutionsRequest,
			@PathVariable String code) {

		solutionRepository.save(new Solution(null, addSolutionsRequest.getName(), addSolutionsRequest.getDescription(),
				dieseRepository.findByCode(code).get()));

		Map<String, String> response = new HashMap<>();
		response.put("status", "created");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("{code}/symptoms/{symptomsCode}")
	public ResponseEntity<Object> deletSymptoms(@PathVariable String code, @PathVariable String symptomsCode) {

		Diese diese = dieseRepository.findByCode(code).get();
		diese.deleteSymptom(symtomRepository.findByCode(symptomsCode).get());
		dieseRepository.save(diese);

		Map<String, String> response = new HashMap<>();
		response.put("status", "Deleted");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("{code}")
	public ResponseEntity<Object> delete(@PathVariable String code) {
		Diese diese = dieseRepository.findByCode(code).get();
		dieseRepository.delete(diese);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("OK"));
	}

	@PutMapping("{code}")
	public ResponseEntity<Object> update(@PathVariable String code, @Valid @RequestBody EditDieseRequest request) {
		Diese diese = dieseRepository.findByCode(code).get();
		diese.setName(request.getName());
		diese.setDescription(request.getDescription());
		dieseRepository.save(diese);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("OK"));
	}

	@PostMapping
	public ResponseEntity<Object> save(@Valid @RequestBody CreateDieseRequest request) {
		Diese diese = new Diese();
		diese.setCode(request.getCode());
		diese.setDescription(request.getDescription());
		diese.setName(request.getName());
		dieseRepository.save(diese);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("OK"));
	}
}
