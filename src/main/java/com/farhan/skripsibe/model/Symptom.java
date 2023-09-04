package com.farhan.skripsibe.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder
@Entity
@Table(name = "gejala")
public class Symptom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "kode", nullable = false)
	private String code;

	@Column(name = "nama", nullable = false)
	private String name;

	@Column(name = "nilai_ds", nullable = false)
	private BigDecimal dsValue;

	@ManyToMany(mappedBy = "symptoms", cascade = {})
	@Setter(AccessLevel.NONE)
	private final List<Diese> dieses = new ArrayList<>();

}
