package com.farhan.skripsibe.model.converter;

import java.util.List;

import com.farhan.skripsibe.model.json.ResultJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class DieseJsonConverter implements AttributeConverter<List<ResultJson>, String> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<ResultJson> dieses) {
		try {
			return objectMapper.writeValueAsString(dieses);
		} catch (final JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<ResultJson> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<List<ResultJson>>() {
			});

		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
