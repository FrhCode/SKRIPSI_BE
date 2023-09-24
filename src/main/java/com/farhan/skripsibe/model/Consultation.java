package com.farhan.skripsibe.model;

import java.time.LocalDateTime;
import java.util.List;

import com.farhan.skripsibe.model.converter.DieseJsonConverter;
import com.farhan.skripsibe.model.converter.SymtomJsonConverter;
import com.farhan.skripsibe.model.json.ResultJson;
import com.farhan.skripsibe.model.json.SymtomJson;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

	@Convert(converter = DieseJsonConverter.class)
	@Column(columnDefinition = "TEXT", name = "hasil diagnosa", nullable = false)
	private List<ResultJson> results;

	@Convert(converter = SymtomJsonConverter.class)
	@Column(columnDefinition = "TEXT", name = "list gejala", nullable = false)
	private List<SymtomJson> symptoms;
}
