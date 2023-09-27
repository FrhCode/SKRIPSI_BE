package com.farhan.skripsibe.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.entities.MassData;
import com.farhan.skripsibe.entities.MassFuntion;
import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.model.json.ReportJson;
import com.farhan.skripsibe.model.json.ReportJson.Known;
import com.farhan.skripsibe.repository.DieseRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DempsterShaferService {
	private final DieseRepository dieseRepository;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DempsterShaferObject {
		private MassFuntion massFuntion;
		private ReportJson report;
	}

	public DempsterShaferObject calculate(List<Symptom> symptoms) {
		List<MassFuntion> massFuntions = convertSymptomsToMassFuntion(symptoms);
		List<ReportJson.Known> knowns = convertSymptomsToKnowns(symptoms);
		List<List<ReportJson.MassData>> calculationData = new ArrayList<>();
		List<List<String>> mCombinationList = new ArrayList<>();

		while (massFuntions.size() > 1) {
			Map<String, Object> combine = combine(massFuntions.get(0), massFuntions.get(1));
			MassFuntion massFuntion = (MassFuntion) combine.get("combine");
			List<ReportJson.MassData> massDataListCombination = (List<ReportJson.MassData>) combine
					.get("massDataListCombination");
			List<String> clearedMcombinationList = (List<String>) combine
					.get("clearedMcombinationList");
			calculationData.add(massDataListCombination);
			mCombinationList.add(clearedMcombinationList);
			massFuntions.remove(0);
			massFuntions.remove(0);
			massFuntions.add(0, massFuntion);
		}

		MassFuntion massFuntion = massFuntions.get(0);

		ReportJson report = new ReportJson(new Date() + ".pdf".toString(), knowns, calculationData, mCombinationList);
		return new DempsterShaferObject(massFuntion, report);

	}

	private List<Known> convertSymptomsToKnowns(List<Symptom> symptoms) {
		List<Known> Knowns = symptoms.stream().map(symptom -> {

			List<String> diesesCode = dieseRepository.findBySymptomCode(symptom.getCode()).stream().map(diese -> {
				return diese.getCode();
			}).collect(Collectors.toList());

			String dieses = symptom.getName() + " (" + String.join(", ", diesesCode) + ")";
			return new ReportJson.Known(dieses,
					symptom.getDsValue().setScale(2, RoundingMode.HALF_UP).toString());
		}).collect(Collectors.toList());

		return Knowns;
	}

	private Map<String, Object> combine(MassFuntion m1, MassFuntion m2) {
		List<MassData> massDataList = new ArrayList<>();

		List<ReportJson.MassData> massDataListCombination = new ArrayList<>();
		massDataListCombination.add(new ReportJson.MassData(null, null));

		for (MassData massData : m2.getMassDataList()) {
			massDataListCombination.add(new ReportJson.MassData(massData.getDieses(),
					massData.getValue().setScale(2, RoundingMode.HALF_UP).toString()));
		}

		for (MassData massData1 : m1.getMassDataList()) {
			massDataListCombination.add(new ReportJson.MassData(massData1.getDieses(),
					massData1.getValue().setScale(2, RoundingMode.HALF_UP).toString()));
			for (MassData massData2 : m2.getMassDataList()) {
				List<String> diese = getIntersection(massData1.getDieses(), massData2.getDieses());
				BigDecimal value = massData1.getValue().multiply(massData2.getValue()).setScale(2, RoundingMode.HALF_UP);

				MassData massData = new MassData(diese, value);
				massDataListCombination.add(new ReportJson.MassData(massData.getDieses(),
						massData.getValue().setScale(2, RoundingMode.HALF_UP).toString()));
				massDataList.add(massData);
			}
		}
		Map<String, Object> mapCombine = combine(massDataList);
		MassFuntion combine = (MassFuntion) mapCombine.get("massFuntion");
		List<String> clearedMcombinationList = (List<String>) mapCombine.get("clearedMcombinationList");

		Map<String, Object> data = new HashMap<>();
		data.put("combine", combine);
		data.put("massDataListCombination", massDataListCombination);
		data.put("clearedMcombinationList", clearedMcombinationList);
		return data;
	}

	private Map<String, Object> combine(List<MassData> massDataList) {
		BigDecimal totalValueOfEmptyDieses = new BigDecimal(0);

		Map<List<String>, MassData> massDataSet = new HashMap<>();
		Map<List<String>, String> mCombinationSet = new HashMap<>();

		String whenNoMatch = "";

		for (MassData massData : massDataList) {
			if (massData.getDieses().contains("empty")) {
				boolean isEmpty = whenNoMatch.length() == 0;
				if (isEmpty) {
					whenNoMatch = massData.getValue().setScale(2, RoundingMode.HALF_UP).toString();
				} else {
					whenNoMatch = whenNoMatch + " + " + massData.getValue().setScale(2, RoundingMode.HALF_UP);
				}
				totalValueOfEmptyDieses = totalValueOfEmptyDieses.add(massData.getValue()).setScale(2, RoundingMode.HALF_UP);
			} else {
				if (massDataSet.containsKey(massData.getDieses())) {
					MassData existingMassData = massDataSet.get(massData.getDieses());
					String existingNumber = mCombinationSet.get(massData.getDieses());

					existingMassData
							.setValue(existingMassData.getValue().add(massData.getValue()).setScale(2, RoundingMode.HALF_UP));

					massDataSet.put(massData.getDieses(), existingMassData);
					mCombinationSet.put(massData.getDieses(),
							existingNumber + " + " + massData.getValue().setScale(2, RoundingMode.HALF_UP).toString());
				} else {
					massDataSet.put(massData.getDieses(), massData);
					boolean isTeta = massData.getDieses().size() == 0;

					if (isTeta) {
						mCombinationSet.put(massData.getDieses(),
								"o " + "(" + massData.getValue().setScale(2, RoundingMode.HALF_UP).toString());
					} else {
						mCombinationSet.put(massData.getDieses(),
								String.join(", ", massData.getDieses()) + " ("
										+ massData.getValue().setScale(2, RoundingMode.HALF_UP).toString());
					}
				}
			}
		}

		MassFuntion massFuntion = new MassFuntion();

		List<MassData> clearedMassDataList = new ArrayList<>(massDataSet.values());
		List<String> clearedMcombinationList = new ArrayList<>();

		for (int i = 0; i < clearedMassDataList.size(); i++) {
			MassData massData = clearedMassDataList.get(i);
			BigDecimal value = massData.getValue().divide(
					BigDecimal.ONE.subtract(totalValueOfEmptyDieses.setScale(2, RoundingMode.HALF_UP)), 6,
					RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
			massData.setValue(value);
			massFuntion.addMassDataList(massData);

			List<String> temp = new ArrayList<>(mCombinationSet.values());
			String text = temp.get(i);
			text = text + ") / (1 - " + totalValueOfEmptyDieses.setScale(2, RoundingMode.HALF_UP) + ")";
			clearedMcombinationList.add(text);
			clearedMcombinationList.add(value.toString());
		}

		boolean isThereisEmptySet = totalValueOfEmptyDieses.compareTo(BigDecimal.ZERO) > 0;

		if (isThereisEmptySet) {
			whenNoMatch = "{} " + whenNoMatch;
			clearedMcombinationList.add(0, totalValueOfEmptyDieses.toString());
			clearedMcombinationList.add(0, whenNoMatch);
		}

		Map<String, Object> data = new HashMap<>();
		data.put("massFuntion", massFuntion);
		data.put("clearedMcombinationList", clearedMcombinationList);
		return data;
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
