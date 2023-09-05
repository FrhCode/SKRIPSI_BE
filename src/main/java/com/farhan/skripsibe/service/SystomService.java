package com.farhan.skripsibe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.SymtomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystomService {
	private final SymtomRepository symtomRepository;

	public List<Symptom> paginate() {
		return null;
	}
}
