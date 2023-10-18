package com.farhan.skripsibe.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSymptomRequest {
	@Size(min = 3, message = "Code setidaknya terdiri dari 3 karakter")
	private String code;
	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@NotNull(message = "dsValue tidak boleh null")
	@DecimalMin(value = "0.0", inclusive = false, message = "dsValue harus lebih besar dari nol")
	@DecimalMax(value = "1.0", message = "dsValue harus kurang dari atau sama dengan 1")
	private BigDecimal dsValue;
}
