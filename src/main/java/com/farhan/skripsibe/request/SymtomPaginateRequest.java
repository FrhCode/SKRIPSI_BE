package com.farhan.skripsibe.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymtomPaginateRequest {
	private Integer pageNumber = 1;
	private Integer pageSize = 5;
	private String query = "";

}
