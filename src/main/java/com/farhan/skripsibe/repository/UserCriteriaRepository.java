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
import com.farhan.skripsibe.model.User;
import com.farhan.skripsibe.request.PaginateUserRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserCriteriaRepository {
	private final EntityManager em;

	public Page<User> paginate(PaginateUserRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> symtomRoot = cq.from(User.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, symtomRoot);

		List<User> datas = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long dataCount = getPaginateCount(request);

		return new PageImpl<>(datas, pageable, dataCount);
	}

	private long getPaginateCount(PaginateUserRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<User> symtomRoot = cq.from(User.class);

		Predicate predicate = getPredicate(request, symtomRoot, cb);

		cq.select(cb.count(symtomRoot));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(PaginateUserRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(PaginateUserRequest request, CriteriaBuilder cb, CriteriaQuery<User> cq,
			Root<User> symtomRoot) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(symtomRoot.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(symtomRoot.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(PaginateUserRequest request, Root<User> symtomRoot,
			CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(cb.equal(cb.literal(1), cb.literal(1)));

		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
