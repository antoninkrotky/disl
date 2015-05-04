package org.disl.pattern;

import static org.junit.Assert.*
import groovy.sql.Sql

import org.disl.meta.Base
import org.junit.Assert
import org.junit.Test

public class ExecuteSQLScriptStep extends Step {
	boolean ignoreErrors=false;
	String commandSeparator=";";
	int updatedRowCount;
	def sql;

	Sql getSql() {
		if (sql instanceof Closure) {
			return sql.call();
		}
		sql
	}

	@Override
	public void execute() {
		try {
		code.split(commandSeparator).each {
			executeSqlStatement(it)
		}
		getSql().commit()
		} catch (Exception e) {
			getSql().rollback()
			throw e
		}
	}

	protected executeSqlStatement(String sqlCommand) {
		if (''.equals(sqlCommand.trim())) {
			return
		}
		try {
			updatedRowCount=getSql().executeUpdate(sqlCommand)
		} catch (Exception e) {
			if (!ignoreErrors) {
				throw new RuntimeException("Error executing ${this}. SQL statement: $sqlCommand",e)
			}
		}
	}
	
	@Test
	void testName() {
		def element=new Base()
		pattern="DROP ${element.name}"
		Assert.assertEquals("DROP Base", code)
	}
}
