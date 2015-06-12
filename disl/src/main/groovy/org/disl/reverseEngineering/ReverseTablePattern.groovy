/*
 * Copyright 2015 Karel H�bl <karel.huebl@gmail.com>.
 *
 * This file is part of disl.
 *
 * Disl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Disl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Disl.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.disl.reverseEngineering


import org.disl.meta.Column
import org.disl.meta.MetaFactory
import org.disl.meta.Table
import org.disl.pattern.FileOutputStep
import org.disl.pattern.Pattern
import org.disl.pattern.Step
import org.junit.Test

class ReverseTablePattern extends Pattern {
	File outputDir=new File("src")
	String packageName
	String parentClassName
	Table table

	@Override
	public Collection<Step> getSteps() {
		File directory=new File(outputDir,packageName.replace('.', '/'))
		File file=new File(directory,"${table.name}.groovy")
		return [new FileOutputStep(name: "Groovy table definition",pattern: getCode(),file: file)];
	}

	String getCode() {
		"""\
package $packageName

import org.disl.meta.*

@Description(\"""$table.description\""")
class $table.name extends ${parentClassName} {

$columnDefinitions		
}"""
	}

	String getColumnDefinitions() {
		getTable().getColumns().collect {getColumnDefinitionCode(it)}.join("\n\n")
	}

	String getColumnDefinitionCode(Column column) {
		"""\
		@Description(\"""$column.description\""")
		@DataType("$column.dataType")
		Column $column.name"""
	}
}









