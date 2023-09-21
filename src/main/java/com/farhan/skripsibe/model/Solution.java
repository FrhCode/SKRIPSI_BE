package com.farhan.skripsibe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "diese" })
@ToString(exclude = { "diese" })
@Entity
@Table(name = "solusi")
public class Solution {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nama")
	private String name;
	@Column(name = "deskripsi", columnDefinition = "TEXT")
	private String description;

	@ManyToOne
	@JoinColumn(name = "id_penyakit")
	private Diese diese;
}
