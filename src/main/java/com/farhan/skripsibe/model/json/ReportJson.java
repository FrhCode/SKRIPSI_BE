package com.farhan.skripsibe.model.json;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	private List<List<MassData>> calculationData;
	private List<List<String>> mCombinationList;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Known {
		private String diese;
		private String belief;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MassData {
		private List<String> dieses = new ArrayList<>();
		private String value;
	}

}
