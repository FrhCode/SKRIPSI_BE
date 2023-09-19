package com.farhan.skripsibe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.request.SymtomPaginateRequest;
import com.farhan.skripsibe.request.SymtomSearchRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SymtomCriteriaRepository {
	private final EntityManager em;

	public List<Symptom> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Symptom> cq = cb.createQuery(Symptom.class);

		Root<Symptom> symtomRoot = cq.from(Symptom.class);

		return em.createQuery(cq.select(symtomRoot)).getResultList();
	}

	public List<Symptom> findByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Symptom> cq = cb.createQuery(Symptom.class);

		Root<Symptom> symtomRoot = cq.from(Symptom.class);

		Predicate nameLike = cb.like(symtomRoot.get("name"), "%" + name + "%");
		cq.where(nameLike);

		return em.createQuery(cq).getResultList();
	}

	public List<Symptom> search(SymtomSearchRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Symptom> cq = cb.createQuery(Symptom.class);

		Root<Symptom> symtomRoot = cq.from(Symptom.class);

		cq.orderBy(cb.desc(symtomRoot.get("id")));

		List<Predicate> predicates = new ArrayList<>();

		if (request.getName() != null) {
			Predicate nameLike = cb.like(symtomRoot.get("name"), "%" + request.getName() + "%");
			predicates.add(nameLike);
		}

		if (request.getDsValue() != null) {
			Predicate dsValueEqual = cb.equal(symtomRoot.get("dsValue"), request.getDsValue());
			predicates.add(dsValueEqual);
		}

		if (request.getCode() != null) {
			Predicate codeLike = cb.like(symtomRoot.get("code"), "%" + request.getCode() + "%");
			predicates.add(codeLike);

		}

		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		return em.createQuery(cq).getResultList();
	}

	public Page<Symptom> paginate(SymtomPaginateRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Symptom> cq = cb.createQuery(Symptom.class);

		Root<Symptom> symtomRoot = cq.from(Symptom.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, symtomRoot);

		List<Symptom> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(SymtomPaginateRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Symptom> symtomRoot = cq.from(Symptom.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);

		cq.select(cb.count(symtomRoot));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(SymtomPaginateRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(SymtomPaginateRequest request, CriteriaBuilder cb, CriteriaQuery<Symptom> cq,
			Root<Symptom> symtomRoot) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(symtomRoot.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(symtomRoot.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(SymtomPaginateRequest request, Root<Symptom> symtomRoot, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		if (request.getQuery() != null) {
			Predicate nameLike = cb.like(symtomRoot.get("name"), "%" + request.getQuery() + "%");
			predicates.add(nameLike);

			Predicate codeLike = cb.like(symtomRoot.get("code"), "%" + request.getQuery() + "%");
			predicates.add(codeLike);
		}

		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
