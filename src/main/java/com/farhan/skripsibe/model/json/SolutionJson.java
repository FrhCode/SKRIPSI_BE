package com.farhan.skripsibe.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolutionJson {
	private String name;
	private String description;
}
