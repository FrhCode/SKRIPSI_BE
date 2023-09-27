package com.farhan.skripsibe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.farhan.skripsibe.entities.MassData;
import com.farhan.skripsibe.entities.MassFuntion;
import com.farhan.skripsibe.model.Symptom;

@ActiveProfiles("test_lele")
@SpringBootTest
public class DevDempsterShaferServiceTest {
	@Autowired
	private DempsterShaferService dempsterShaferService;

	@Autowired
	private SymptomService symtomService;

	@Test
	@Transactional
	void case1() {
		Symptom KG19 = symtomService.findByCode("KG19").get();
		Symptom KG20 = symtomService.findByCode("KG20").get();
		List<Symptom> symptoms = List.of(KG19, KG20);

		MassFuntion expected = new MassFuntion(List.of(
				new MassData(List.of("P10"), new BigDecimal("0.9")),
				new MassData(List.of(), new BigDecimal("0.06")),
				new MassData(List.of("P8", "P9", "P10"), new BigDecimal("0.04"))));

		MassFuntion result = dempsterShaferService.calculate(symptoms).getMassFuntion();

		for (int i = 0; i < result.getMassDataList().size(); i++) {

			List<MassData> massDataListResult = result.getMassDataList();

			List<String> resultDieses = massDataListResult.get(i).getDieses();
			BigDecimal roundedResultValue = massDataListResult.get(i).getValue().setScale(2, RoundingMode.HALF_UP);

			List<MassData> massDataListExpected = expected.getMassDataList();

			List<String> expectedDieses = massDataListExpected.get(i).getDieses();
			BigDecimal roundedExpectedValue = massDataListExpected.get(i).getValue().setScale(2, RoundingMode.HALF_UP);

			assertEquals(expectedDieses, resultDieses);
			assertEquals(roundedExpectedValue, roundedResultValue);
		}
	}

	@Test
	@Transactional
	void case2() {
		Symptom KG01 = symtomService.findByCode("KG01").get();
		Symptom KG02 = symtomService.findByCode("KG02").get();
		Symptom KG10 = symtomService.findByCode("KG10").get();
		Symptom KG17 = symtomService.findByCode("KG17").get();
		List<Symptom> symptoms = List.of(KG01, KG02, KG10, KG17);

		MassFuntion expected = new MassFuntion(List.of(
				new MassData(List.of(), new BigDecimal("0.07")),
				new MassData(List.of("P1", "P2", "P4", "P5"), new BigDecimal("0.03")),
				new MassData(List.of("P4"), new BigDecimal("0.55")),
				new MassData(List.of("P8"), new BigDecimal("0.31")),
				new MassData(List.of("P1", "P4", "P5"), new BigDecimal("0.03"))

		)

		);

		MassFuntion result = dempsterShaferService.calculate(symptoms).getMassFuntion();
		System.out.println(result);
		for (int i = 0; i < result.getMassDataList().size(); i++) {

			List<MassData> massDataListResult = result.getMassDataList();

			List<String> resultDieses = massDataListResult.get(i).getDieses();
			BigDecimal roundedResultValue = massDataListResult.get(i).getValue().setScale(2, RoundingMode.HALF_UP);

			List<MassData> massDataListExpected = expected.getMassDataList();

			List<String> expectedDieses = massDataListExpected.get(i).getDieses();
			BigDecimal roundedExpectedValue = massDataListExpected.get(i).getValue().setScale(2, RoundingMode.HALF_UP);

			assertEquals(expectedDieses, resultDieses);
			assertEquals(roundedExpectedValue, roundedResultValue);
		}
	}
}