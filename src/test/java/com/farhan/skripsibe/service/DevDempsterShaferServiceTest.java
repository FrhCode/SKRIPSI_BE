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
		Symptom SC19 = symtomService.findByCode("SC19").get();
		Symptom SC20 = symtomService.findByCode("SC20").get();
		List<Symptom> symptoms = List.of(SC19, SC20);

		MassFuntion expected = new MassFuntion(List.of(
				new MassData(List.of("DC10"), new BigDecimal("0.9")),
				new MassData(List.of(), new BigDecimal("0.06")),
				new MassData(List.of("DC8", "DC9", "DC10"), new BigDecimal("0.04"))));

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
		Symptom SC01 = symtomService.findByCode("SC01").get();
		Symptom SC02 = symtomService.findByCode("SC02").get();
		Symptom SC10 = symtomService.findByCode("SC10").get();
		Symptom SC17 = symtomService.findByCode("SC17").get();
		List<Symptom> symptoms = List.of(SC01, SC02, SC10, SC17);

		MassFuntion expected = new MassFuntion(List.of(
				new MassData(List.of(), new BigDecimal("0.07")),
				new MassData(List.of("DC1", "DC2", "DC4", "DC5"), new BigDecimal("0.03")),
				new MassData(List.of("DC4"), new BigDecimal("0.55")),
				new MassData(List.of("DC8"), new BigDecimal("0.31")),
				new MassData(List.of("DC1", "DC4", "DC5"), new BigDecimal("0.03"))

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
