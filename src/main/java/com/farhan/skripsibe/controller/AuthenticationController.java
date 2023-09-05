package com.farhan.skripsibe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.request.LoginRequest;
import com.farhan.skripsibe.request.RegisterRequest;
import com.farhan.skripsibe.response.AuthenticationResponse;
import com.farhan.skripsibe.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.ok(authenticationService.register(registerRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
	}
}