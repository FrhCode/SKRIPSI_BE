package com.farhan.skripsibe.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.DieseCriteriaRepository;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.request.PaginateDieseRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DieseService {
	private final DieseRepository dieseRepository;
	private final DieseCriteriaRepository dieseCriteriaRepository;

	public Optional<Diese> findByCode(String code) {
		return dieseRepository.findByCode(code);
	}

	public long count() {
		return dieseRepository.count();
	}

	public Page<Diese> paginate(PaginateDieseRequest paginateDieseRequest) {
		return dieseCriteriaRepository.paginate(paginateDieseRequest);
	}

	public Set<Symptom> getSymtoms(String code) {
		return dieseRepository.findByCode(code).get().getSymptoms();
	}

}
