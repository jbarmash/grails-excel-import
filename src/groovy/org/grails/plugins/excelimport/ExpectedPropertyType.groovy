package org.grails.plugins.excelimport


public enum ExpectedPropertyType {

	IntType([name: 'number']),
	StringType([name: 'text']),
	DateType([name: 'date']),
	DateJavaType([name: 'date']),
	DoubleType([name: 'number']),
	EmailType([name: 'email']);


	final String userViewableName


	public ExpectedPropertyType(Map parameters = [:]) {
		this.userViewableName = parameters?.name ?: this.name()
	}


}
