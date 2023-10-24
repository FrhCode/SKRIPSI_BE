package com.farhan.skripsibe.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDieseRequest {
	@NotBlank(message = "code can not be empty")
	private String code;

	@NotBlank(message = "name can not be empty")
	private String name;

	@NotBlank(message = "description can not be empty")
	private String description;
}
