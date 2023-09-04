package com.farhan.skripsibe.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "diese" })
@ToString(exclude = { "diese" })
// @Builder
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
