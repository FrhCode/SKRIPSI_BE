package com.farhan.skripsibe.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.repository.ConsultationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {
	private final ConsultationRepository consultationRepository;

	private long countInvoiceForADate(LocalDateTime dateTime) {
		LocalDateTime startOfToday = dateTime.with(LocalTime.MIN); // Start of today
		return consultationRepository.countByInvoiceDateIsAfter(startOfToday);
	}

	public String generateInvoiceNumber(LocalDateTime dateTime) {
		// Get the current date in the YMD format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));

		// Get the current sequence for the day
		long sequence = countInvoiceForADate(dateTime) + 1;

		// Generate the invoice number
		String invoiceNumber = "INV-" + currentDate + "-" + sequence;

		return invoiceNumber;
	}
}
