package com.farhan.skripsibe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.request.SymtomPaginateRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SymtomJpqlRepository {
	private final EntityManager em;

	public Page<Symptom> paginate(SymtomPaginateRequest symtomPaginateRequest) {
		Integer pageNumber = symtomPaginateRequest.getPage();
		Integer pageSize = symtomPaginateRequest.getSize();
		String query = symtomPaginateRequest.getQuery();
		String sortBy = symtomPaginateRequest.getSortBy();

		TypedQuery<Symptom> typedQuery = em
				.createQuery("SELECT s FROM Symptom s WHERE name LIKE CONCAT('%', :name, '%') ORDER BY :sortBy ASC",
						Symptom.class)
				.setParameter("sortBy", sortBy)
				.setParameter("name", query);
		// TypedQuery<Symptom> typedQuery = entityManager
		// .createQuery("SELECT s FROM Symptom s WHERE name LIKE CONCAT('%', :name, '%')
		// ORDER BY s.name ASC",
		// Symptom.class)
		// .setParameter("name", query);

		Integer count = paginateCount(typedQuery);

		List<Symptom> symtoms = typedQuery
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize)
				.getResultList();

		return new PageImpl<>(symtoms,
				PageRequest.of(pageNumber, pageSize, Sort.by(Direction.fromString("ASC"), sortBy)), count);
	}

	private Integer paginateCount(TypedQuery<Symptom> typedQuery) {
		List<Symptom> symtoms = typedQuery.getResultList();
		return symtoms.size();
	}
}
