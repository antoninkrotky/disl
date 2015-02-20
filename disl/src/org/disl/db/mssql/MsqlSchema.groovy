package org.disl.db.mssql

import groovy.sql.Sql

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLClientInfoException;

import org.disl.meta.PhysicalSchema
import org.junit.Test

class MsqlSchema extends PhysicalSchema {
	String host
	int port=1433
	String databaseName
	String instance
	
	MsqlSchema() {
		jdbcDriver="net.sourceforge.jtds.jdbc.Driver"
	}
	
	public String getJdbcUrl() {
		"jdbc:jtds:sqlserver://${getHost()}:${getPort()}/${getDatabaseName()};instance=${getInstance()};user=${getUser()};password=${getPassword()};"
	}
	
	public Sql getSql() {
		//Pass username & password to jdbcUrl to prevent SSO error when ntlmauth.dll is not on path.
		Sql.newInstance(getJdbcUrl(),getJdbcDriver());
	}

}
