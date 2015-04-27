package org.grails.plugins.excelImport

import grails.plugins.*

class ExcelImportGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.1 > *"
    // resources that are excluded from plugin packaging
    

    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def author = "Jean Barmash, Oleksiy Symonenko"

    def authorEmail = "Jean.Barmash@gmail.com"
    def title = "Excel, Excel 2007 & CSV Importer Using Apache POI"
    def description = '''\\
	Excel-Import plugin uses Apache POI [http://poi.apache.org/] library (v 3.6) to parse Excel files.  
      	It's useful for either bootstrapping data, or when you want to allow your users to enter some data using Excel spreadsheets. 
'''

	def license = "APACHE"
	def organization = [ name: "EnergyScoreCards.com", url: "http://www.energyscorecards.com/" ]
	def developers = [
        	[ name: "Oleksiy Symonenko", email: "" ],
	]

	def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPXLIMPORT" ]
	def scm = [ url: "https://github.com/jbarmash/grails-excel-import" ]
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/excel-import"

}
