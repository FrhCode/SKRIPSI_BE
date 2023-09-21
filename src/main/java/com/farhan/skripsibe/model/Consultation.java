package com.farhan.skripsibe.model;

import java.util.List;
import java.time.LocalDateTime;

import com.farhan.skripsibe.model.converter.DieseResultConverter;
import com.farhan.skripsibe.model.converter.SymtomResultConverter;
import com.farhan.skripsibe.model.json.DieseJson;
import com.farhan.skripsibe.model.json.SymtomJson;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@ToString(exclude = {})
@Entity
@Table(name = "konsultasi")
public class Consultation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "kode invoice", nullable = false, unique = true)
	private String invoice;

	@Column(name = "nama pasien", nullable = false)
	private String patientName;

	@Column(name = "tanggal konsultasi", nullable = false)
	private LocalDateTime invoiceDate;

	@Convert(converter = DieseResultConverter.class)
	@Column(columnDefinition = "TEXT", name = "hasil diagnosa", nullable = false)
	private List<DieseJson> dieses;

	@Convert(converter = SymtomResultConverter.class)
	@Column(columnDefinition = "TEXT", name = "list gejala", nullable = false)
	private List<SymtomJson> symptoms;
}
