package com.farhan.skripsibe.model.json;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DieseJson {
	private String code;

	private String name;

	private String description;

	private List<SolutionJson> solutions;

}
