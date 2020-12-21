package com.github.petrosdimitris.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public abstract class BaseManager<T> {
	@Resource
	protected PlatformTransactionManager platformTransactionManager;

	private EntityManager entityManager;
	private Class<T> type;

	public BaseManager(Class<T> type) {
		this.type = type;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected <X> X inTransaction(TransactionCallback<X> transactionCallback) {
		return new TransactionTemplate(platformTransactionManager).execute(transactionCallback);
	}

	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return getSession().getCriteriaBuilder();
	}

	protected Root<T> root() {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery();
		return query.from(type);
	}

	public List<T> search(Long first, Long count) {
		return inTransaction(new TransactionCallback<List<T>>() {

			@Override
			public List<T> doInTransaction(TransactionStatus status) {
				Criteria criteria = getSession().createCriteria(type);
				criteria.setFirstResult(first.intValue());
				criteria.setMaxResults(count.intValue());
				List<T> items = criteria.list();
				return items;
			}

		});
	}

	public Long searchCount() {
		return inTransaction(new TransactionCallback<Long>() {

			@Override
			public Long doInTransaction(TransactionStatus status) {
				Criteria criteria = getSession().createCriteria(type);
				criteria.setProjection(Projections.rowCount());
				return (Long) criteria.list().get(0);
			}

		});
	}
}
