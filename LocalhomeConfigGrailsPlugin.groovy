import grails.plugins.localhome.LocalConfigUtil
import grails.plugins.localhome.BasenamesUtils

import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource
import org.springframework.context.support.ReloadableResourceBundleMessageSource

import org.apache.commons.io.FileUtils

class LocalhomeConfigGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "1.2 > *"
    def loadAfter = ['i18n']
    def pluginExcludes = [
        "grails-app/views/test/*.gsp",
        "grails-app/controllers/localhome/config/TestController.groovy",
        "grails-app/i18n/*"
    ]

    def title = "Local Config Plugin"
    def author = "Ford Guo"
    def authorEmail = ""
    def description = 'Configure the external configuration in ~/.grails/appName/files(Config.groovy,grails-app/i18n,web-app/)'
    def documentation = "http://grails.org/plugin/localhome-config"

    def license = "APACHE"
    def issueManagement = [system: 'Github', url: 'https://github.com/fordguo/grails-localhome-config/issues']
    def scm = [url: "https://github.com/fordguo/grails-localhome-config"]

    def doWithApplicationContext = { applicationContext ->

        Set baseNames = []

        def i18nNames = LocalConfigUtil.checkI18nNames(application)
        def messageSource = applicationContext.getBean("messageSource")
        if (messageSource) {
            messageSource.basenames = ((baseNames as List)+ (i18nNames as List)).toArray()
            messageSource.clearCache() 
        }
        def webappFiles = LocalConfigUtil.checkWebappFiles(application)
        def servletContext = application.getParentContext().getServletContext()
        def startStr = "web-app"
        webappFiles.each {from->
            int idx = from.absolutePath.indexOf(startStr)+startStr.size()
            def resPath = from.absolutePath[idx..-1]
            def to = new File(servletContext.getRealPath(resPath))
            if (to.exists() && from.lastModified() != to.lastModified()) {
                FileUtils.copyFile(from,to,true)
            }
        }
    }
}
