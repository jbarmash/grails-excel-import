class ExcelImportGrailsPlugin {
    // the plugin version
    def version = "1.0.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0.0 > *"
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

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before 
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }
}
