package grails.plugins.localhome

import org.apache.commons.io.FileUtils

class LocalConfigUtil {
    static final String SEP = File.separator
    static def checkWebappFiles(grailsApplication) {
        def fromDir = appHomeDir(grailsApplication)
        def files = []
        if (fromDir) {
            def webappDir = new File(fromDir,"web-app")
            if (webappDir.exists()) {
                webappDir.eachFileRecurse{if (it.isFile()) files<<it}
            }
        }
        return files
    }
    private static def appHomeDir(grailsApplication) {
        def appDir = new File(System.properties["user.home"]+SEP+".grails"+SEP+grailsApplication.metadata['app.name'])
        if (appDir.exists() && appDir.isDirectory()) {
            return appDir
        }else{
            return null
        }
    }
    static def checkI18nNames(grailsApplication) {
        def fromDir = appHomeDir(grailsApplication)
        Set names = []
        if (fromDir) {
            def i18nDir = new File(fromDir,"grails-app"+SEP+"i18n")
            if (i18nDir.exists()) i18nDir.eachFileMatch  ~/.*\.properties/, { names<<"file:"+BasenamesUtils.getBasename(it.absolutePath)}
        }
        return names
    }
    
}
