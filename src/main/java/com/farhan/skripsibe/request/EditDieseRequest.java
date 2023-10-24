package com.farhan.skripsibe.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditDieseRequest {
	@NotBlank(message = "name can not be empty")
	private String name;

	@NotBlank(message = "description can not be empty")
	private String description;

}
