package com.farhan.skripsibe.response;

import java.util.Date;

import com.farhan.skripsibe.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	private User user;
	private String jwtToken;
	private Date expires;

}
