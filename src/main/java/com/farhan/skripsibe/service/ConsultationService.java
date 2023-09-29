package com.farhan.skripsibe.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.model.json.ReportJson;
import com.farhan.skripsibe.model.json.ResultJson;
import com.farhan.skripsibe.model.json.SymtomJson;
import com.farhan.skripsibe.repository.ConsultationCriteriaRepository;
import com.farhan.skripsibe.repository.ConsultationRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.request.ConsultationRequest;
import com.farhan.skripsibe.request.PaginateConsultationRequest;
import com.farhan.skripsibe.service.DempsterShaferService.DempsterShaferObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {
	private final InvoiceService invoiceService;
	private final ConsultationRepository consultationRepository;
	private final ConsultationCriteriaRepository consultationCriteriaRepository;
	private final SymtomRepository symtomRepository;
	private final DempsterShaferService dempsterShaferService;
	private final ConverterService converterService;

	public Consultation save(Consultation consultation) {
		String invoice = invoiceService.generateInvoiceNumber(consultation.getInvoiceDate());
		consultation.setInvoice(invoice);
		return consultationRepository.save(consultation);
	}

	public List<Consultation> findAll() {
		return consultationRepository.findAll();
	}

	public List<Consultation> findAll(Pageable pageable) {
		Page<Consultation> page = consultationRepository.findAll(pageable);
		return page.getContent();
	}

	public Consultation findByinvoice(String invoiceNumber) {
		return consultationRepository.findByinvoice(invoiceNumber).get();
	}

	public long count() {
		return consultationRepository.count();
	}

	public Page<Consultation> paginate(PaginateConsultationRequest consultationPaginateRequest) {
		return consultationCriteriaRepository.paginate(consultationPaginateRequest);
	}

	public Consultation save(ConsultationRequest consultationRequest) {
		List<Symptom> symptoms = symtomRepository.findByCodesIn(consultationRequest.getSymptoms());
		DempsterShaferObject result = dempsterShaferService.calculate(symptoms);

		String invoiceCode = invoiceService.generateInvoiceNumber();

		List<ResultJson> consultationResults = converterService.massFuntionToResultJsonList(result.getMassFuntion());
		ReportJson report = result.getReport();
		List<SymtomJson> symtomJsonsList = converterService.symptomsTosymtomJsonList(symptoms);

		Consultation consultation = new Consultation(null, invoiceCode, consultationRequest.getName(), LocalDateTime.now(),
				consultationRequest.getAddress(), consultationRequest.getPhoneNumber(),
				consultationResults,
				symtomJsonsList,
				report);

		return consultationRepository.save(consultation);
	}
}
