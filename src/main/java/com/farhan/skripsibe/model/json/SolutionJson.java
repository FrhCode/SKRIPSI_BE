package com.farhan.skripsibe.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionJson {
	@JsonProperty("nama")
	private String name;
	@JsonProperty("deskripsi")
	private String description;
}
