package com.farhan.skripsibe.request;

import java.util.List;

import com.farhan.skripsibe.annotation.ValidSymptoms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSymptomsRequest {
	@ValidSymptoms
	private List<String> symptomsCode;
}
