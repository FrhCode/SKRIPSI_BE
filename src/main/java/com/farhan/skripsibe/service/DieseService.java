package com.farhan.skripsibe.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.repository.DieseCriteriaRepository;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.request.PaginateDieseRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DieseService {
	private final DieseRepository dieseRepository;
	private final DieseCriteriaRepository dieseCriteriaRepository;

	public long count() {
		return dieseRepository.count();
	}

	public Page<Diese> paginate(PaginateDieseRequest paginateDieseRequest) {
		return dieseCriteriaRepository.paginate(paginateDieseRequest);
	}

}
