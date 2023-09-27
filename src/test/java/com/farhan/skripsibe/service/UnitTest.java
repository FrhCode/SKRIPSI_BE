package com.farhan.skripsibe.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class UnitTest {
	@Test
	public void pg1() {
		String number = "";

		for (int i = 0; i < 100; i++) {
			BigDecimal num = new BigDecimal(i * .5);
			System.out.println(num);
			number = number + num;
		}

		System.out.println(number);
	}

	@Test
	public void pg2() throws ParseException {
		Date currentDate = new Date();
		String inputDateTime = "2023-09-27T16:01:13.000+00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date date = dateFormat.parse(inputDateTime);
		currentDate.before(date);
		System.out.println("Converted date: " + date);
	}
}
