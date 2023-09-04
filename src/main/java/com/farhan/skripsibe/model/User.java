package com.farhan.skripsibe.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
// @Builder
@Entity
@Table(indexes = {
		@Index(name = "unique_email_constraint", columnList = "email", unique = true)
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String email;

	@Column(name = "no_hp", nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String password;

}
