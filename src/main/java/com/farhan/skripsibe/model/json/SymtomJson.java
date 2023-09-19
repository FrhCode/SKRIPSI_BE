package com.farhan.skripsibe.model.json;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymtomJson {
	private String name;

	private String code;

	private BigDecimal dsValue;
}
