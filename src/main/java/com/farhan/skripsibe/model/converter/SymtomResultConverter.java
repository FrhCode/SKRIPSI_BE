package com.farhan.skripsibe.model.converter;

import java.util.List;
import com.farhan.skripsibe.model.json.SymtomJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
public class SymtomResultConverter implements AttributeConverter<List<SymtomJson>, String> {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<SymtomJson> symtom) {
		try {
			return objectMapper.writeValueAsString(symtom);
		} catch (final JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public List<SymtomJson> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<List<SymtomJson>>() {
			});

		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
