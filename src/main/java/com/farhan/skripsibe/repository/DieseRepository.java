package com.farhan.skripsibe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.farhan.skripsibe.model.Diese;

public interface DieseRepository extends JpaRepository<Diese, Long> {
	Optional<Diese> findByCode(String code);

	@Query("SELECT DISTINCT d from Diese d JOIN d.symptoms s WHERE s.code = :code")
	List<Diese> findBySymptomCode(String code);

	long count();
}
