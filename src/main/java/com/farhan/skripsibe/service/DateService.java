package com.farhan.skripsibe.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class DateService {

	public LocalDateTime getRandomDateTimeBetween(LocalDateTime start, LocalDateTime end) {
		long minEpochDay = start.toLocalDate().toEpochDay();
		long maxEpochDay = end.toLocalDate().toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minEpochDay, maxEpochDay);

		LocalDateTime randomDateTime = LocalDateTime.of(start.toLocalDate().plusDays(randomDay - minEpochDay),
				start.toLocalTime());

		long minSecond = start.toLocalTime().toSecondOfDay();
		long maxSecond = end.toLocalTime().toSecondOfDay();
		long randomSecond = ThreadLocalRandom.current().nextLong(minSecond, maxSecond);

		return randomDateTime.plus(randomSecond, ChronoUnit.SECONDS);
	}
}
