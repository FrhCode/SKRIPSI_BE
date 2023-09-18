package com.farhan.skripsibe.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "symptoms" })
@ToString(exclude = { "symptoms" })
// @Builder
@Entity
@Table(name = "penyakit")
public class Diese {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "kode", nullable = false, unique = true)
	private String code;

	@Column(name = "nama", nullable = false)
	private String name;

	@Column(name = "deskripsi", nullable = false, columnDefinition = "TEXT")
	private String description;

	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(joinColumns = { @JoinColumn(name = "id_penyakit") }, inverseJoinColumns = {
			@JoinColumn(name = "id_gejala") }, name = "rule_base")
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private final List<Symptom> symptoms = new ArrayList<>();

	@OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "diese")
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private final List<Solution> solutions = new ArrayList<>();

	// public void addSolution(Solution solution) {
	// solutions.add(solution);
	// }

	// public void deleteSolution(Solution solution) {
	// solutions.remove(solution);
	// }

	public void addSymptom(Symptom symptom) {
		symptoms.add(symptom);
	}

	public void deleteSymptom(Symptom symptom) {
		symptoms.remove(symptom);
	}
}
