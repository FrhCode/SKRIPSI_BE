package com.farhan.skripsibe.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymtomSearchRequest {
	private String code;
	private String name;
	private BigDecimal dsValue;
}
