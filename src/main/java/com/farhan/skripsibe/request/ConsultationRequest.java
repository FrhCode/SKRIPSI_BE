package com.farhan.skripsibe.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationRequest {
	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@NotBlank(message = "Nomor telepon wajib diisi")
	private String phoneNumber;

	@NotBlank(message = "Alamat wajib diisi")
	private String address;

	@Size(min = 2, message = "Minimal gejala adalah 2")
	private List<String> symptoms;
}
