package com.farhan.skripsibe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.exception.BadRequestException;
import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Solution;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.request.AddSolutionsRequest;
import com.farhan.skripsibe.request.AddSymptomsRequest;
import com.farhan.skripsibe.request.PaginateDieseRequest;
import com.farhan.skripsibe.response.BaseResponse;
import com.farhan.skripsibe.service.DieseService;
import com.farhan.skripsibe.service.SymptomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/dieses")
@RequiredArgsConstructor
public class DieseController {
	private final DieseService dieseService;
	private final SymptomService symtomService;
	private final DieseRepository dieseRepository;
	private final SymtomRepository symtomRepository;

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

	@PutMapping("{code}/symptoms/add")
	public ResponseEntity<Object> addSymtoms(@Valid @RequestBody AddSymptomsRequest addSymptomsRequest,
			@PathVariable String code) {
		List<String> symptomsCode = addSymptomsRequest.getSymptomsCode();

		boolean isValid = symtomService.checkSymptomsCodeValid(symptomsCode);

		if (!isValid) {
			Map<String, String> errors = new HashMap<>();
			errors.put("symptomsCode", "invalid symptomsCode");

			BadRequestException responseBody = new BadRequestException(errors);

			System.out.println(responseBody);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody.getResponseBody());

		}

		Diese P1 = dieseRepository.findByCode(code).orElseThrow();

		P1.addSymptom(symtomRepository.findByCode(symptomsCode.get(0)).get());

		dieseRepository.save(P1);

		Map<String, String> response = new HashMap<>();
		response.put("status", "created");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("{code}/solutions/add")
	public ResponseEntity<Object> addSolution(@Valid @RequestBody AddSolutionsRequest addSolutionsRequest,
			@PathVariable String code) {
		return null;
	}
}
