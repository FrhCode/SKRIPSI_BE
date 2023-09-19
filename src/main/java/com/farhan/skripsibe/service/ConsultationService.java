package com.farhan.skripsibe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.repository.ConsultationCriteriaRepository;
import com.farhan.skripsibe.repository.ConsultationRepository;
import com.farhan.skripsibe.request.ConsultationPaginateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {
	private final InvoiceService invoiceService;
	private final ConsultationRepository consultationRepository;
	private final ConsultationCriteriaRepository consultationCriteriaRepository;

	public Consultation save(Consultation consultation) {
		String invoice = invoiceService.generateInvoiceNumber();
		consultation.setInvoice(invoice);
		return consultationRepository.save(consultation);
	}

	public List<Consultation> findAll() {
		return consultationRepository.findAll();
	}

	public Consultation findByinvoice(String invoiceNumber) {
		return consultationRepository.findByinvoice(invoiceNumber);
	}

	public long count() {
		return consultationRepository.count();
	}

	public Page<Consultation> paginate(ConsultationPaginateRequest consultationPaginateRequest) {
		return consultationCriteriaRepository.paginate(consultationPaginateRequest);
	}
}
