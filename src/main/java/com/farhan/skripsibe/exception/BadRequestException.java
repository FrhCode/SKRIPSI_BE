package com.farhan.skripsibe.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class BadRequestException {
	Map<String, Object> responseBody = new HashMap<>();

	public BadRequestException(Map<String, String> errors) {
		responseBody = new HashMap<>();
		responseBody.put("message", "Validation failed");
		responseBody.put("errors", errors);
		responseBody.put("timestamp", ZonedDateTime.now().toString());
	}
}
