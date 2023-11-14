package com.farhan.skripsibe.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
	@NotBlank(message = "code can not be empty")
	private String name;

	@NotBlank(message = "name can not be empty")
	private String email;

	@NotBlank(message = "description can not be empty")
	private String phoneNumber;

	private String profileImage = "/images/2024-lamborghini-revuelto-127-641a1d518802b.jpg";

	@NotBlank(message = "password can not be empty")
	private String password;
}
