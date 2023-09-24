package com.farhan.skripsibe.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MassFuntion {
	private List<MassData> massDataList = new ArrayList<>();

	public void sort() {
		Comparator<? super MassData> comparator = new Comparator<MassData>() {

			@Override
			public int compare(MassData arg0, MassData arg1) {
				return arg1.getValue().compareTo(arg0.getValue());
			}

		};
		massDataList.sort(comparator);
	}

	public void addMassDataList(MassData data) {
		massDataList.add(data);
	}
}
