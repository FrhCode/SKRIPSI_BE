package com.farhan.skripsibe.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.farhan.skripsibe.model.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
	long countByInvoiceDateIsAfter(LocalDateTime startOfToday);

	Optional<Consultation> findByinvoice(String invoice);

	long count();
}
