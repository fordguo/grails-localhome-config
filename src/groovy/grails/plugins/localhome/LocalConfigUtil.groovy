package grails.plugins.localhome

import org.apache.commons.io.FileUtils

class LocalConfigUtil {
    static final String SEP = File.separator
    static def checkHomeFiles(grailsApplication) {
        def fromDir = new File(System.properties["user.home"]+SEP+".grails"+SEP+grailsApplication.metadata['app.name'])
        def files = []
        fromDir.eachFileRecurse{files<<it}
        return files
    }
}
