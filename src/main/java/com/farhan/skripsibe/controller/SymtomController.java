package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.farhan.skripsibe.exception.BadRequestException;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.request.CreateSymptomRequest;
import com.farhan.skripsibe.request.PaginateSymtomRequest;
import com.farhan.skripsibe.request.SymtomSearchRequest;
import com.farhan.skripsibe.request.EditSymptomRequest;
import com.farhan.skripsibe.response.BaseResponse;
import com.farhan.skripsibe.response.MessageResponse;
import com.farhan.skripsibe.service.SymptomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/symptoms")
@RequiredArgsConstructor
public class SymtomController {
	private final SymptomService symtomService;
	private final SymtomRepository symtomRepository;

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

	@DeleteMapping("{code}")
	public ResponseEntity<MessageResponse<String>> delete(@PathVariable String code) {
		Symptom symptom = symtomRepository.findByCode(code).get();
		symtomRepository.delete(symptom);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("OK"));
	}

	@PutMapping("{code}")
	public ResponseEntity<MessageResponse<String>> update(@PathVariable String code,
			@Valid @RequestBody EditSymptomRequest request) {
		Symptom symptom = symtomRepository.findByCode(code).get();
		symptom.setDsValue(request.getDsValue());
		symptom.setName(request.getName());
		symtomRepository.save(symptom);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("OK"));
	}

	@PostMapping
	public ResponseEntity<Object> createSymtoms(@Valid @RequestBody CreateSymptomRequest createSymptomRequest) {
		Optional<Symptom> optionalSymptom = symtomRepository.findByCode(createSymptomRequest.getCode());
		if (optionalSymptom.isPresent()) {
			Map<String, String> errros = new HashMap<>();
			errros.put("code", "code telah digunakan");
			BadRequestException badRequestException = new BadRequestException(errros);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getResponseBody());
		}
		Symptom symptom = new Symptom(null, createSymptomRequest.getCode(), createSymptomRequest.getName(),
				createSymptomRequest.getDsValue());

		symtomService.create(symptom);

		Map<String, String> response = new HashMap<>();
		response.put("status", "created");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
