package com.farhan.skripsibe.model.json;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DieseJson {
	@JsonProperty("nama")
	private String name;

	@JsonProperty("persentasi")
	private BigDecimal percentage;

	@JsonProperty("deskripsi")
	private String description;

	@JsonProperty("solusi")
	private List<SolutionJson> solutions;

}
