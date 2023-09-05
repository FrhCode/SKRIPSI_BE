package com.farhan.skripsibe.response;

import java.util.List;

import com.farhan.skripsibe.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	private String username;
	private String email;
	private List<Role> roles;
	private String jwtToken;

}
