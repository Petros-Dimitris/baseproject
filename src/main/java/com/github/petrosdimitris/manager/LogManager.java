package com.github.petrosdimitris.manager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.github.petrosdimitris.domain.Log;
import com.github.petrosdimitris.search.LogSearch;

public class LogManager extends BaseManager<Log, LogSearch> {

	public LogManager() {
		super(Log.class);
	}

	protected void addOrder(LogSearch search, Criteria criteria) {
		criteria.addOrder(Order.desc("date"));
	}

}
