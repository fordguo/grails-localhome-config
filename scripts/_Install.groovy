//
// This script is executed by Grails after plugin was installed to project.
// This script is a Gant script so you can use all special variables provided
// by Gant (such as 'baseDir' which points on project base dir). You can
// use 'ant' to access a global instance of AntBuilder
//
// For example you can create directory under project tree:
//
//    ant.mkdir(dir:"${basedir}/grails-app/jobs")
//

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
