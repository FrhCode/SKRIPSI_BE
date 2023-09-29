package com.farhan.skripsibe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.model.User;
import com.farhan.skripsibe.repository.UserRepository;
import com.farhan.skripsibe.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository userRepository;

	@GetMapping("all")
	public BaseResponse<User> find() {
		List<User> users = userRepository.findAll();
		return new BaseResponse<User>(users);
	}
}
