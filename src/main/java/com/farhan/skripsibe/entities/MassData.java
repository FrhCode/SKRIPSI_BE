package com.farhan.skripsibe.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MassData {
	// list.size = 0, berarti teta
	// list.mengandung empty, berarti waktu perhitungan mass ngak intersection antar
	// penyakit
	private List<String> diese = new ArrayList<>();
	private BigDecimal value = new BigDecimal(0);
}
