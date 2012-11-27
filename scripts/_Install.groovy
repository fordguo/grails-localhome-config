def homeConfigStr = "\${userHome}/.grails/\${appName}/Config.groovy"
def configStr = "${basedir}/grails-app/conf/Config.groovy"
def configGroovy = new File(configStr)
if (configGroovy.text.indexOf(homeConfigStr)==-1) {
    ant.copy(file: configStr, tofile: configStr+System.currentTimeMillis())
    configGroovy.append '''
/**
*   check the external configure file: user home: ~/.grails/<app_name>/Config.groovy
*/
grails.config.locations = []
def f = new File("${userHome}/.grails/${appName}/Config.groovy")
if (f.exists()) {
    grails.config.locations << "file:${userHome}/.grails/${appName}/Config.groovy"
}
'''
}
