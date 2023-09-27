package com.farhan.skripsibe.service;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.Role;
import com.farhan.skripsibe.model.User;
import com.farhan.skripsibe.repository.RoleRepository;
import com.farhan.skripsibe.repository.UserRepository;
import com.farhan.skripsibe.request.LoginRequest;
import com.farhan.skripsibe.request.RegisterRequest;
import com.farhan.skripsibe.response.AuthenticationResponse;

import io.jsonwebtoken.Claims;
import lombok.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final RoleRepository roleRepository;

	public AuthenticationResponse register(RegisterRequest request) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhoneNumber(null);
		user.setUsername(request.getUsername());

		Role role = roleRepository.findByName("User").get();
		user.addRole(role);

		String jwtToken = jwtService.generateToken(user);
		Date expDate = jwtService.extractClaim(jwtToken, Claims::getExpiration);
		return new AuthenticationResponse(user, jwtToken, expDate);
	}

	public AuthenticationResponse authenticate(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()));
		User user = userRepository.findByEmail(request.getEmail()).get();

		String jwtToken = jwtService.generateToken(user);

		System.out.println(new Date(System.currentTimeMillis()));
		Date expDate = jwtService.extractClaim(jwtToken, Claims::getExpiration);
		return new AuthenticationResponse(user, jwtToken, expDate);
	}

}
