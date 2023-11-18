package com.farhan.skripsibe.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditUserRequest {
	@NotBlank(message = "code can not be empty")
	private String name;

	@NotBlank(message = "description can not be empty")
	private String phoneNumber;
}
