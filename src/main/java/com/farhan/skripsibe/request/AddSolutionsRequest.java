package com.farhan.skripsibe.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSolutionsRequest {
	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@Size(min = 3, message = "Deskripsi setidaknya terdiri dari 3 karakter")
	private String description;
}
