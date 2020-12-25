package com.github.petrosdimitris.manager;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.petrosdimitris.domain.User;

public class SignInManager {

	private static Logger logger = LogManager.getLogger(SignInManager.class);

	@Resource
	private PlatformTransactionManager platformTransactionManager;
	private EntityManager entityManager;

	public SignInManager() {
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Roles signIn(String username, String password) {
		return new TransactionTemplate(platformTransactionManager).execute(new TransactionCallback<Roles>() {

			@Override
			public Roles doInTransaction(TransactionStatus status) {
				Criteria criteria = entityManager.unwrap(Session.class).createCriteria(User.class);
				criteria.add(Restrictions.eq("username", username));
				criteria.add(Restrictions.eq("password", encypt(username, password)));

				List list = criteria.list();
				if (list.size() != 1) {
					return null;
				} else {
					User user = (User) list.get(0);
					logger.info("user " + username + " signed in");
					return new Roles(user.getUserRoles());
				}
			}

		});

	}

	protected String encypt(String username, String password) {
		return password;
	}

}
