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

@ActiveProfiles("test-case-1")
@SpringBootTest
public class Case1DempsterShaferServiceTest {

	@Autowired
	private DempsterShaferService dempsterShaferService;

	@Autowired
	private SymptomService symtomService;

	@Test
	@Transactional
	void case1() {
		Symptom KG01 = symtomService.findByCode("KG01").get();
		Symptom KG02 = symtomService.findByCode("KG02").get();
		Symptom KG08 = symtomService.findByCode("KG08").get();
		Symptom KG07 = symtomService.findByCode("KG07").get();
		List<Symptom> symptoms = List.of(KG01, KG02, KG07, KG08);

		MassFuntion expected = new MassFuntion(List.of(
				new MassData(List.of("P2"), new BigDecimal(0.82)),
				new MassData(List.of(), new BigDecimal(0.1296)),
				new MassData(List.of("P1", "P2", "P3"), new BigDecimal(0.0504))));

		MassFuntion result = dempsterShaferService.calculate(symptoms);

		for (int i = 0; i < result.getMassDataList().size(); i++) {

			List<MassData> massDataListResult = result.getMassDataList();

			List<String> resultDieses = massDataListResult.get(i).getDieses();
			BigDecimal roundedResultValue = massDataListResult.get(i).getValue().setScale(4, RoundingMode.HALF_UP);

			List<MassData> massDataListExpected = expected.getMassDataList();

			List<String> expectedDieses = massDataListExpected.get(i).getDieses();
			BigDecimal roundedExpectedValue = massDataListExpected.get(i).getValue().setScale(4, RoundingMode.HALF_UP);

			assertEquals(expectedDieses, resultDieses);
			assertEquals(roundedExpectedValue, roundedResultValue);
		}

	}
}
