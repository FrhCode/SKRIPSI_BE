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

@ActiveProfiles("test-dev")
@SpringBootTest
public class DevDempsterShaferServiceTest {
	@Autowired
	private DempsterShaferService dempsterShaferService;

	@Autowired
	private SymtomService symtomService;

	@Test
	@Transactional
	void case1() {
		Symptom KG19 = symtomService.findByCode("KG19").get();
		Symptom KG20 = symtomService.findByCode("KG20").get();
		List<Symptom> symptoms = List.of(KG19, KG20);

		MassFuntion expected = new MassFuntion(List.of(
				new MassData(List.of("P10"), new BigDecimal(0.9)),
				new MassData(List.of(), new BigDecimal(0.06)),
				new MassData(List.of("P8", "P9", "P10"), new BigDecimal(0.04))));

		MassFuntion result = dempsterShaferService.calculate(symptoms);

		for (int i = 0; i < result.getMassDataList().size(); i++) {

			List<MassData> massDataListResult = result.getMassDataList();

			List<String> resultDieses = massDataListResult.get(i).getDiese();
			BigDecimal roundedResultValue = massDataListResult.get(i).getValue().setScale(4, RoundingMode.HALF_UP);

			List<MassData> massDataListExpected = expected.getMassDataList();

			List<String> expectedDieses = massDataListExpected.get(i).getDiese();
			BigDecimal roundedExpectedValue = massDataListExpected.get(i).getValue().setScale(4, RoundingMode.HALF_UP);

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
				new MassData(List.of("P4"), new BigDecimal("0.552486")),
				new MassData(List.of("P8"), new BigDecimal("0.309392")),
				new MassData(List.of(), new BigDecimal("0.077348")),
				new MassData(List.of("P1", "P4", "P5"), new BigDecimal("0.041436")),
				new MassData(List.of("P1", "P2", "P4", "P5"), new BigDecimal("0.019337"))));

		MassFuntion result = dempsterShaferService.calculate(symptoms);

		for (int i = 0; i < result.getMassDataList().size(); i++) {

			List<MassData> massDataListResult = result.getMassDataList();

			List<String> resultDieses = massDataListResult.get(i).getDiese();
			BigDecimal roundedResultValue = massDataListResult.get(i).getValue().setScale(4, RoundingMode.HALF_UP);

			List<MassData> massDataListExpected = expected.getMassDataList();

			List<String> expectedDieses = massDataListExpected.get(i).getDiese();
			BigDecimal roundedExpectedValue = massDataListExpected.get(i).getValue().setScale(4, RoundingMode.HALF_UP);

			assertEquals(expectedDieses, resultDieses);
			assertEquals(roundedExpectedValue, roundedResultValue);
		}
	}
}
