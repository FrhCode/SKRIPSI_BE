package com.farhan.skripsibe.model.json;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymtomJson {
	@JsonProperty("nama")
	private String name;

	@JsonProperty("code")
	private String code;

	@JsonProperty("nilai_ds")
	private BigDecimal dsValue;

}
