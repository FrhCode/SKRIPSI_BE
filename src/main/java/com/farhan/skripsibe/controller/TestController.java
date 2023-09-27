package com.farhan.skripsibe.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@GetMapping()
	public UserDetails getMethodName(HttpServletRequest request) {
		final String authHeader = request.getHeader("Authorization");
		final String jwt = authHeader.substring(7);

		String username = jwtService.extractUsername(jwt);

		UserDetails user = userDetailsService.loadUserByUsername(username);

		return user;
	}

}
