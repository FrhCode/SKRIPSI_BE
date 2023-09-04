package com.farhan.skripsibe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farhan.skripsibe.model.Diese;

public interface DieseRepository extends JpaRepository<Diese, Long> {
	Optional<Diese> findByCode(String code);
}
