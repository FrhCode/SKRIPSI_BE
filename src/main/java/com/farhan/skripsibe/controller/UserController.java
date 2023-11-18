package com.farhan.skripsibe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.User;
import com.farhan.skripsibe.repository.UserRepository;
import com.farhan.skripsibe.request.ChangePasswordRequest;
import com.farhan.skripsibe.request.CreateUserRequest;
import com.farhan.skripsibe.request.EditUserRequest;
import com.farhan.skripsibe.request.PaginateDieseRequest;
import com.farhan.skripsibe.request.PaginateUserRequest;
import com.farhan.skripsibe.response.BaseResponse;
import com.farhan.skripsibe.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
	private final UserRepository userRepository;

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("all")
	public BaseResponse<User> find() {
		List<User> users = userRepository.findAll();
		return new BaseResponse<User>(users);
	}

	// create router for pagination
	@GetMapping
	public Page<User> paginate(PaginateUserRequest paginateUserRequest) {
		return userService.paginate(paginateUserRequest);
	}

	// create route for create user
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setName(createUserRequest.getName());
		user.setEmail(createUserRequest.getEmail());
		user.setPhoneNumber(createUserRequest.getPhoneNumber());
		user.setProfileImage(createUserRequest.getProfileImage());
		user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

		userRepository.save(user);
		Map<String, String> response = new HashMap<>();
		response.put("status", "created");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// create route for delete user by id
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		userRepository.deleteById(id);
		Map<String, String> response = new HashMap<>();
		response.put("status", "deleted");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// create route for edit user by id
	@PutMapping("{id}")
	public ResponseEntity<Object> edit(@PathVariable Long id, @Valid @RequestBody EditUserRequest editUserRequest) {
		User user = userRepository.findById(id).get();
		user.setName(editUserRequest.getName());
		user.setPhoneNumber(editUserRequest.getPhoneNumber());

		userRepository.save(user);
		Map<String, String> response = new HashMap<>();
		response.put("status", "updated");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// create route for update user password
	@PutMapping("{id}/password")
	public ResponseEntity<Object> changePassword(@PathVariable Long id,
			@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		User user = userRepository.findById(id).get();
		user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));

		userRepository.save(user);
		Map<String, String> response = new HashMap<>();
		response.put("status", "updated");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
