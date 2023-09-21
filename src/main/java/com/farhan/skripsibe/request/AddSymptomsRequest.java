package com.farhan.skripsibe.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSymptomsRequest {
	private List<String> symptomsCode;
}
