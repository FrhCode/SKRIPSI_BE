package com.farhan.skripsibe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.farhan.skripsibe.model.Symptom;

public interface SymtomRepository extends JpaRepository<Symptom, Long> {
	Optional<Symptom> findByCode(String code);

	@Query("SELECT s FROM Symptom s WHERE s.code IN :codes")
	List<Symptom> findByCodesIn(List<String> codes);

	long count();
}
