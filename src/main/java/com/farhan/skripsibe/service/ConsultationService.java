package com.farhan.skripsibe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.repository.ConsultationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {
	private final InvoiceService invoiceService;
	private final ConsultationRepository consultationRepository;

	public Consultation save(Consultation consultation) {
		String invoice = invoiceService.generateInvoiceNumber();
		consultation.setInvoice(invoice);
		return consultationRepository.save(consultation);
	}

	public List<Consultation> findAll() {
		return consultationRepository.findAll();
	}
}
