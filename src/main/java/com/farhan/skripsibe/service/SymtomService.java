package com.farhan.skripsibe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomCriteriaRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.request.PaginateSymtomRequest;
import com.farhan.skripsibe.request.SymtomSearchRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SymtomService {
	private final SymtomCriteriaRepository symtomCriteriaRepository;
	private final SymtomRepository symtomRepository;

	public List<Symptom> findAll() {
		return symtomCriteriaRepository.findAll();
	}

	public List<Symptom> search(SymtomSearchRequest request) {
		return symtomCriteriaRepository.search(request);
	}

	public Page<Symptom> paginate(PaginateSymtomRequest symtomPaginateRequest) {
		return symtomCriteriaRepository.paginate(symtomPaginateRequest);
	}

	public long count() {
		return symtomRepository.count();
	}

}
