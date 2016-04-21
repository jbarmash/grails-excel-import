includeTargets << grailsScript('Init')
includeTargets << new File("${codenarcPluginDir}/scripts/Codenarc.groovy")
includeTargets << new File("${gmetricsPluginDir}/scripts/Gmetrics.groovy")
 
configClassname = 'Config'
 
target(main: "Add some style to the gmetrics report") {
  depends(compile, codenarc, gmetrics)
 
  stylizeGmetrics()
  stylizeCodenarc()
}
 
private void stylizeGmetrics() {
  println "add some style to the gmetrics report"
  ant.mkdir(dir: 'target/gmetrics')
  ant.xslt style: "test/report-templates/gmetrics.xslt", out: "target/gmetrics/gmetrics.html", in: 'target/gmetrics.xml'
  ant.copy(todir: 'target/gmetrics') {
    fileset(dir: 'test/report-templates/') {
      include name: 'default.css'
      include name: '*.png'
      include name: '*.gif'
    }
  }
}
 
private void stylizeCodenarc() {
  println "Add some style to the codenarc report"
  ant.mkdir(dir: 'target/codenarc')
  ant.xslt style: "test/report-templates/codenarc.xslt", out: "target/codenarc/codenarc.html", in: 'target/codenarc.xml'
  ant.copy(todir: 'target/codenarc') {
    fileset(dir: 'test/report-templates') {
      include name: 'default.css'
      include name: '*.png'
      include name: '*.gif'
    }
  }
}
 
setDefaultTarget(main)

