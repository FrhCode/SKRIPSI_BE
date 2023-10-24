package com.farhan.skripsibe.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditSymptomRequest {
	@NotBlank(message = "name can not be empty")
	private String name;

	@NotNull(message = "dsValue can not be empty")
	private BigDecimal dsValue;

}
