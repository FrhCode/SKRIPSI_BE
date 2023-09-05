package com.farhan.skripsibe.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = { "users" })
@ToString(exclude = { "users" })
@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "roles")
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private final List<User> users = new ArrayList<>();
}
