package com.farhan.skripsibe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.farhan.skripsibe.model.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
	@Query("SELECT s from Solution s where s.diese.id = ?1")
	List<Solution> findByDieseId(Long diese_id);
}
