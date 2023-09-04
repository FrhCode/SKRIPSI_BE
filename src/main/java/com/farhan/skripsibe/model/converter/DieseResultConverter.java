package com.farhan.skripsibe.model.converter;

import java.util.List;

import com.farhan.skripsibe.model.json.DieseJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class DieseResultConverter implements AttributeConverter<List<DieseJson>, String> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<DieseJson> dieses) {
		try {
			return objectMapper.writeValueAsString(dieses);
		} catch (final JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<DieseJson> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<List<DieseJson>>() {
			});

		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
