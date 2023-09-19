package com.farhan.skripsibe.model.json;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DieseJson {
	private String name;

	private BigDecimal percentage;

	private String description;

	private List<SolutionJson> solutions;

}
