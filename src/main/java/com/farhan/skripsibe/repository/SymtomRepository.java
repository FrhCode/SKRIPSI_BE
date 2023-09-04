package com.farhan.skripsibe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farhan.skripsibe.model.Symptom;

public interface SymtomRepository extends JpaRepository<Symptom, Long> {
	Optional<Symptom> findByCode(String code);
}
