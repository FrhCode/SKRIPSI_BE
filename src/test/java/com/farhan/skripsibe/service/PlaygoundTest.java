package com.farhan.skripsibe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.Data;

@SpringBootTest
public class PlaygoundTest {

	@Test
	void pg2() {
		Students students = new Students();
		students.setNames(List.of());
		if (students.getNames() == null) {
			System.out.println("Empty");
		} else
			System.out.println("OK");
	}

	@Data
	public class Students {
		private List<String> names;
	}

	@Test
	void pg3() {
		List<String> names = List.of("farhan");
		System.out.println(names.isEmpty());
	}

	@Test
	void pg4() {
		Map<List<String>, String> studentMaps = new HashMap<>();

		studentMaps.put(List.of("Mohammad", "Farhan"), "Mohammad Farhan");

		System.out.println(studentMaps.containsKey(List.of("Mohammad", "Farhan")));

	}

	@Test
	void pg5() {
		Map<List<String>, String> studentMaps = new HashMap<>();

		studentMaps.put(List.of("Mohammad", "Farhan"), "Mohammad Farhan");

		System.out.println(studentMaps.containsKey(List.of("Mohammad", "Farhan")));
		assertEquals(1, 1);
	}

	@Test
	void pg6() {
		BigDecimal number1 = new BigDecimal("0.00498294");
		BigDecimal number2 = new BigDecimal("0.00418294");

		int decimalPlaces = 4;

		BigDecimal roundedNumber1 = number1.setScale(decimalPlaces, RoundingMode.HALF_UP);
		BigDecimal roundedNumber2 = number2.setScale(decimalPlaces, RoundingMode.HALF_UP);

		System.out.println("Original number 1: " + number1);
		System.out.println("Rounded number 1: " + roundedNumber1);

		System.out.println("Original number 2: " + number2);
		System.out.println("Rounded number 2: " + roundedNumber2);

	}

	@Test
	void pg7() {
		BigDecimal number1 = new BigDecimal("0.00498294");
		number1 = number1.add(new BigDecimal(2));

		System.out.println(number1);

	}

	@Test
	void pg8() {
		BigDecimal massDataValue = new BigDecimal("0.00560000");
		BigDecimal totalValueOfEmptyDieses = new BigDecimal("0.71040000");

		// Calculate the denominator separately
		BigDecimal denominator = BigDecimal.ONE.subtract(totalValueOfEmptyDieses);

		BigDecimal value = massDataValue.divide(denominator, 6, RoundingMode.HALF_UP);
		System.out.println(value);
	}
}
