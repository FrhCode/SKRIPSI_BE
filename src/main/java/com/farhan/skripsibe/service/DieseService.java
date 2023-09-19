package com.farhan.skripsibe.service;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.repository.DieseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DieseService {
	private final DieseRepository dieseRepository;

	public long count() {
		return dieseRepository.count();
	}

}
