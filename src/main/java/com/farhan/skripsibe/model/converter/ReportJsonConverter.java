package com.farhan.skripsibe.model.converter;

import com.farhan.skripsibe.model.json.ReportJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class ReportJsonConverter implements AttributeConverter<ReportJson, String> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ReportJson arg0) {
		try {
			return objectMapper.writeValueAsString(arg0);
		} catch (final JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public ReportJson convertToEntityAttribute(String arg0) {
		try {
			return objectMapper.readValue(arg0, new TypeReference<ReportJson>() {
			});

		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
