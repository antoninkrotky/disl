/*
 * Copyright 2015 - 2016 Karel H�bl <karel.huebl@gmail.com>.
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
package org.disl.meta

import groovy.transform.CompileStatic

import java.lang.reflect.Field

/**
 * Abstract parent class for DISL meta classes.
 * */
@CompileStatic
abstract class Base implements Comparable<Base> {
	
	String name
	
	public String getName() {
		if (name==null) {
			return this.getClass().getSimpleName()
		}
		name
	}
	
	public String toString() {
		getName()
	}
	
	public List<Field> getFieldsByType(Class matchType) {
		getFieldsByType(this.getClass(), matchType);
	}
	
	public List<Field> getFieldsByType(Class type,Class matchType) {
		List<Field> fields=[]
		if (type.getSuperclass()!=null) {
			fields.addAll(getFieldsByType(type.getSuperclass(),matchType))
		}
		fields.addAll(type.getDeclaredFields().findAll {matchType.isAssignableFrom(it.getType())})
		return fields
	}
	
	public Field getFieldByName(String name) {
		return getFieldByName(name, this.getClass())
	}
	
	public Field getFieldByName(String name,Class type) {
		try {
			return type.getDeclaredField(name)
		} catch (NoSuchFieldException e) {
			if (type.getSuperclass()) {
				return getFieldByName(name,type.getSuperclass())
			}
			return null
		}
	}
	
	public List getPropertyValuesByType(Class type) {
		return metaClass.properties.findAll({type.isAssignableFrom(it.type)}).collect({this.getProperty(it.name)});
	}
	
	public List<String> getPropertyNamesByType(Class type) {
		return metaClass.properties.findAll({type.isAssignableFrom(it.type)}).collect({it.name});
	}
	
	@Override
	public int compareTo(Base o) {
		return this.getName().compareTo(o.getName())
	}

}
