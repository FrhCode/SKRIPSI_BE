package com.farhan.skripsibe.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.farhan.skripsibe.model.User;
import com.farhan.skripsibe.repository.UserCriteriaRepository;
import com.farhan.skripsibe.request.PaginateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserCriteriaRepository userCriteriaRepository;

	public Page<User> paginate(PaginateUserRequest paginateUserRequest) {
		return userCriteriaRepository.paginate(paginateUserRequest);
	}
}
