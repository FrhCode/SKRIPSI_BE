package com.farhan.skripsibe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.request.PaginateDieseRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DieseCriteriaRepository {
	private final EntityManager em;

	public Page<Diese> paginate(PaginateDieseRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Diese> cq = cb.createQuery(Diese.class);

		Root<Diese> symtomRoot = cq.from(Diese.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, symtomRoot);

		List<Diese> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(PaginateDieseRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Diese> symtomRoot = cq.from(Diese.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);

		cq.select(cb.count(symtomRoot));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(PaginateDieseRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(PaginateDieseRequest request, CriteriaBuilder cb, CriteriaQuery<Diese> cq,
			Root<Diese> symtomRoot) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(symtomRoot.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(symtomRoot.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(PaginateDieseRequest request, Root<Diese> symtomRoot,
			CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.equal(cb.literal(1), cb.literal(1)));

		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
