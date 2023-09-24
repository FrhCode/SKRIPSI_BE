package com.farhan.skripsibe.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.entities.MassData;
import com.farhan.skripsibe.entities.MassFuntion;
import com.farhan.skripsibe.model.Symptom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DempsterShaferService {

	public MassFuntion calculate(List<Symptom> symptoms) {
		List<MassFuntion> massFuntions = convertSymptomsToMassFuntion(symptoms);

		while (massFuntions.size() > 1) {
			MassFuntion massFuntion = combine(massFuntions.get(0), massFuntions.get(1));
			massFuntions.remove(0);
			massFuntions.remove(0);
			massFuntions.add(0, massFuntion);
		}

		MassFuntion massFuntion = massFuntions.get(0);
		massFuntion.sort();

		return massFuntion;

	}

	private MassFuntion combine(MassFuntion m1, MassFuntion m2) {
		List<MassData> massDataList = new ArrayList<>();

		for (MassData massData1 : m1.getMassDataList()) {
			for (MassData massData2 : m2.getMassDataList()) {
				List<String> diese = getIntersection(massData1.getDiese(), massData2.getDiese());
				BigDecimal value = massData1.getValue().multiply(massData2.getValue());

				MassData massData = new MassData(diese, value);
				massDataList.add(massData);
			}
		}
		MassFuntion combine = combine(massDataList);

		return combine;
	}

	private MassFuntion combine(List<MassData> massDataList) {
		BigDecimal totalValueOfEmptyDieses = new BigDecimal(0);

		Map<List<String>, MassData> massDataSet = new HashMap<>();

		for (MassData massData : massDataList) {
			if (massData.getDiese().contains("empty")) {
				totalValueOfEmptyDieses = totalValueOfEmptyDieses.add(massData.getValue());
			} else {
				if (massDataSet.containsKey(massData.getDiese())) {
					MassData existingMassData = massDataSet.get(massData.getDiese());
					existingMassData.setValue(existingMassData.getValue().add(massData.getValue()));
					massDataSet.put(massData.getDiese(), existingMassData);
				} else {
					massDataSet.put(massData.getDiese(), massData);
				}
			}
		}

		MassFuntion massFuntion = new MassFuntion();

		List<MassData> clearedMassDataList = new ArrayList<>(massDataSet.values());

		for (MassData massData : clearedMassDataList) {
			BigDecimal value = massData.getValue().divide(BigDecimal.ONE.subtract(totalValueOfEmptyDieses), 6,
					RoundingMode.HALF_UP);
			massData.setValue(value);
			massFuntion.addMassDataList(massData);
		}

		return massFuntion;
	}

	private List<MassFuntion> convertSymptomsToMassFuntion(List<Symptom> symptoms) {
		List<MassFuntion> MassFuntionValues = symptoms.stream().map(symptom -> {
			List<String> diesesCodeFromSymptom = symptom.getDieses().stream().map(diese -> {
				return diese.getCode();
			}).collect(Collectors.toList());

			List<MassData> massDatas = new ArrayList<>();
			massDatas.add(new MassData(diesesCodeFromSymptom, symptom.getDsValue()));
			massDatas.add(new MassData(new ArrayList<>(), BigDecimal.ONE.subtract(symptom.getDsValue())));

			return new MassFuntion(massDatas);
		}).collect(Collectors.toList());

		return MassFuntionValues;
	}

	private List<String> getIntersection(List<String> list1, List<String> list2) {
		List<String> result = new ArrayList<>();

		boolean isList1NotEmpty = !list1.isEmpty();
		boolean isList2NotEmpty = !list2.isEmpty();

		if (isList1NotEmpty && isList2NotEmpty) {
			boolean hasIntersection = false;

			for (String str : list1) {
				if (list2.contains(str)) {
					result.add(str);
					hasIntersection = true;
				}
			}

			if (!hasIntersection) {
				return List.of("empty");
			}
		} else if (isList1NotEmpty) {
			result.addAll(list1);
		} else if (isList2NotEmpty) {
			result.addAll(list2);
		}

		return result;
	}
}
