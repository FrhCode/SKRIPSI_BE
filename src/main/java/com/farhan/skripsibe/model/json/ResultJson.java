package com.farhan.skripsibe.model.json;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultJson {
	private List<DieseJson> dieses;
	private BigDecimal percentage;
}
