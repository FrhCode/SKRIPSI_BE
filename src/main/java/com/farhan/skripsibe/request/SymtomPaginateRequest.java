package com.farhan.skripsibe.request;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymtomPaginateRequest {
	private Integer page = 0;
	private Integer size = 5;
	private String query = "";

	private String sortBy = "id";
	private Sort.Direction sortDirection = Sort.Direction.ASC;

}
