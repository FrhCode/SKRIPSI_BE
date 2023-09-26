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
	private List<Diese> dieses;
	private BigDecimal percentage;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Diese {
		private String code;

		private String name;

		private String description;

		private List<Solution> solutions;

		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Solution {
			private String name;
			private String description;
		}

	}

}
