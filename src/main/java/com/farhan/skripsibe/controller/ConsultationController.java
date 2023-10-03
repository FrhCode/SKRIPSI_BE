package com.farhan.skripsibe.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.repository.ConsultationRepository;
import com.farhan.skripsibe.request.ConsultationRequest;
import com.farhan.skripsibe.request.PaginateConsultationRequest;
import com.farhan.skripsibe.response.BaseResponse;
import com.farhan.skripsibe.service.ConsultationService;
import com.farhan.skripsibe.service.PdfService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/consultations")
@RequiredArgsConstructor
public class ConsultationController {
	private final ConsultationService consultationService;
	private final PdfService pdfService;
	private final ConsultationRepository consultationRepository;

	@GetMapping("{consultationInvoice}")
	public Consultation find(@PathVariable String consultationInvoice) {
		return consultationService.findByinvoice(consultationInvoice);
	}

	@GetMapping("{consultationInvoice}/download")
	public ResponseEntity<Resource> donwloadReport(@PathVariable String consultationInvoice)
			throws FileNotFoundException {
		Consultation consultation = consultationRepository.findByinvoice(consultationInvoice).get();
		String generateConsultationReport = pdfService.generateConsultationReport(consultation.getReport());

		File file = new File(generateConsultationReport);
		Resource resource = new FileSystemResource(file);

		ContentDisposition contentDisposition = ContentDisposition.builder("inline")
				.filename(consultation.getInvoice() + ".pdf")
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok()
				.headers(headers)
				.body(resource);
	}

	@GetMapping("count")
	public Map<String, Object> count() {
		Map<String, Object> response = new HashMap<>();
		long symtomCount = consultationService.count();
		response.put("consultation_count", symtomCount);
		return response;
	}

	@GetMapping("all")
	public BaseResponse<Consultation> all(@RequestParam(required = false) Optional<Integer> size) {
		List<Consultation> consultations = new ArrayList<>();
		if (size.isPresent()) {
			consultations = consultationService.findAll(PageRequest.of(0, size.get()));
		} else {
			consultations = consultationService.findAll();
		}

		return new BaseResponse<Consultation>(consultations);
	}

	@GetMapping
	public Page<Consultation> paginate(PaginateConsultationRequest consultationPaginateRequest) {
		return consultationService.paginate(consultationPaginateRequest);
	}

	@PostMapping
	public Map<String, String> create(@Valid @RequestBody ConsultationRequest consultationRequest) {
		Consultation consultation = consultationService.save(consultationRequest);
		Map<String, String> response = new HashMap<>();
		response.put("invoice", consultation.getInvoice());
		return response;
	}
}
