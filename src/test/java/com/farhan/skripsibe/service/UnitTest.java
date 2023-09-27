package com.farhan.skripsibe.service;

import java.math.BigDecimal;

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
}
