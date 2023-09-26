package com.farhan.skripsibe.model.json;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportJson {
	private String name;
	private List<Known> Knowns;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Known {
		private String diese;
		private String belief;
	}
}
