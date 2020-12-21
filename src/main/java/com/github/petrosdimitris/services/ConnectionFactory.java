package com.github.petrosdimitris.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionFactory {
	private static BasicDataSource dataSource;

	public static String connectionUrl;
	public static String connectionDriverClass;
	public static String connectionUsername;
	public static String connectionPassword;

	private ConnectionFactory() {
	}

	public static Connection getConnection() throws SQLException {
		if (dataSource == null) {
			dataSource = new BasicDataSource();
			dataSource.setUrl(connectionUrl);
			dataSource.setDriverClassName(connectionDriverClass);
			dataSource.setUsername(connectionUsername);
			dataSource.setPassword(connectionPassword);
		}

		return dataSource.getConnection();
	}

	public void setConnectionUrl(String connectionUrl) {
		ConnectionFactory.connectionUrl = connectionUrl;
	}

	public void setConnectionDriverClass(String connectionDriverClass) {
		ConnectionFactory.connectionDriverClass = connectionDriverClass;
	}

	public void setConnectionUsername(String connectionUsername) {
		ConnectionFactory.connectionUsername = connectionUsername;
	}

	public void setConnectionPassword(String connectionPassword) {
		ConnectionFactory.connectionPassword = connectionPassword;
	}
}
