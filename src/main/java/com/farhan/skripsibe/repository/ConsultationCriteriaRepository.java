package com.farhan.skripsibe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.request.ConsultationPaginateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsultationCriteriaRepository {
	private final EntityManager em;

	public Page<Consultation> paginate(ConsultationPaginateRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Consultation> cq = cb.createQuery(Consultation.class);

		Root<Consultation> symtomRoot = cq.from(Consultation.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, symtomRoot);

		List<Consultation> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(ConsultationPaginateRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Consultation> symtomRoot = cq.from(Consultation.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);

		cq.select(cb.count(symtomRoot));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(ConsultationPaginateRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(ConsultationPaginateRequest request, CriteriaBuilder cb, CriteriaQuery<Consultation> cq,
			Root<Consultation> symtomRoot) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(symtomRoot.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(symtomRoot.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(ConsultationPaginateRequest request, Root<Consultation> symtomRoot,
			CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		// log.info("Query " + request.getQuery());
		// if (request.getQuery() != null) {
		// log.info("IF");
		// Predicate id = cb.like(symtomRoot.get("id"), "%" + request.getQuery() + "%");
		// predicates.add(id);
		// }
		// log.info("DONE IF");
		predicates.add(cb.equal(cb.literal(1), cb.literal(1)));
		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
