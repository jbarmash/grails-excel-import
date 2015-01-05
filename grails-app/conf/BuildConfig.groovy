grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }

    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        compile(group: 'org.apache.poi', name: 'poi', version: '3.11');
        //xlxs file support
        compile(group: 'org.apache.poi', name: 'poi-ooxml', version: '3.11') {
            excludes 'xmlbeans'
        }
        compile(group:'joda-time',name:'joda-time',version:'2.6')
        
        //compile group:'org.apache.poi', name:'poi-contrib', version:'3.7'
        //compile group:'org.apache.poi', name:'poi-scratchpad', version:'3.7' //ppt, word, visio, outlook support
    }

    plugins {
        build ':release:3.0.1', ':rest-client-builder:1.0.3'/*,":tomcat:7.0.53"*/,{
            export = false
        }
        //build ":tomcat:7.0.53"

        //		compile ':hibernate:3.6.10.15' {
        //			export = false
        //		}
    }
}
