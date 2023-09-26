package com.farhan.skripsibe.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.entities.MassData;
import com.farhan.skripsibe.entities.MassFuntion;
import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.model.json.ResultJson;
import com.farhan.skripsibe.model.json.SymtomJson;
import com.farhan.skripsibe.repository.DieseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConverterService {
	private final DieseRepository dieseRepository;

	public List<ResultJson> massFuntionToResultJsonList(MassFuntion massFuntion) {
		List<ResultJson> consultationResults = new ArrayList<>();
		for (MassData massData : massFuntion.getMassDataList()) {
			List<String> dieses = massData.getDieses();
			BigDecimal value = massData.getValue();

			List<ResultJson.Diese> dieseJsonList = new ArrayList<>();
			for (String diese : dieses) {
				Diese localDiese = dieseRepository.findByCode(diese).get();

				List<ResultJson.Diese.Solution> solutions = localDiese.getSolutions().stream()
						.map(solution -> {
							return new ResultJson.Diese.Solution(solution.getName(), solution.getDescription());
						}).collect(Collectors.toList());

				dieseJsonList.add(new ResultJson.Diese(localDiese.getCode(), localDiese.getName(), localDiese.getDescription(),
						solutions));
			}
			consultationResults.add(new ResultJson(dieseJsonList, value));
		}

		return consultationResults;
	}

	public List<SymtomJson> symptomsTosymtomJsonList(List<Symptom> symtoms) {
		return symtoms.stream().map(symptom -> {
			return new SymtomJson(symptom.getName(), symptom.getCode(), symptom.getDsValue());
		}).collect(Collectors.toList());
	}
}
