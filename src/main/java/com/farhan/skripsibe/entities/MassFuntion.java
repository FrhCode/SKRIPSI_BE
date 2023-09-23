package com.farhan.skripsibe.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MassFuntion {
	private List<MassData> massDataList = new ArrayList<>();

	public void addMassDataList(MassData data) {
		massDataList.add(data);
	}
}
