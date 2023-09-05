package com.farhan.skripsibe.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.request.SymtomPaginateRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SymtomJpqlRepository {
	private final EntityManager entityManager;

	public Page<Symptom> paginate(SymtomPaginateRequest symtomPaginateRequest) {
		Integer pageNumber = symtomPaginateRequest.getPageNumber() > 0 ? symtomPaginateRequest.getPageNumber() : 1;
		String query = symtomPaginateRequest.getQuery();
		Integer pageSize = symtomPaginateRequest.getPageSize();

		TypedQuery<Symptom> typedQuery = entityManager
				.createQuery("SELECT s FROM Symptom s WHERE s.name LIKE :name", Symptom.class)
				.setParameter("name", "%" + query + "%");

		Integer count = paginateCount(typedQuery);

		List<Symptom> symtoms = typedQuery.setFirstResult((pageNumber - 1) * pageSize)
				.setMaxResults(pageSize)
				.getResultList();

		return new PageImpl<>(symtoms, PageRequest.of(pageNumber, pageSize), count);
	}

	private Integer paginateCount(TypedQuery<Symptom> typedQuery) {
		List<Symptom> symtoms = typedQuery.getResultList();
		return symtoms.size();
	}
}
