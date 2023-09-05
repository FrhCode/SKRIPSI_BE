package com.farhan.skripsibe.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "email can not be empty")
	private String email;
	@NotBlank(message = "password can not be empty")
	private String password;
}