import grails.plugins.localhome.LocalConfigUtil
import grails.plugins.localhome.BasenamesUtils

import org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource

import org.apache.commons.io.FileUtils

class LocalhomeConfigGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
	def loadAfter = ['i18n']
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Local Config Plugin" // Headline display name of the plugin
    def author = "Ford Guo"
    def authorEmail = ""
    def description = '''\
Configure the external configuration in ~/.grails/appName/files(Config.groovy,DataSource.groovy)
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/localhome-config"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"
    
    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/fordguo/grails-localhome-config" ]
    Set baseNames = []
    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        println "doWithSpring"
        def i18nNames = LocalConfigUtil.checkI18nNames(application)
        if (manager?.hasGrailsPlugin("i18n") && i18nNames) {
            def i18nPlugin = manager.getGrailsPlugin("i18n")
            def messageResources
            if (application.warDeployed) {
                messageResources = parentCtx?.getResources("**/WEB-INF/${baseDir}/**/*.properties")?.toList()
            } else {
                messageResources = i18nPlugin.watchedResources
            }
            if (messageResources) {
                BasenamesUtils.i18nCode(messageResources,baseNames)
            }
        }
        messageSource(PluginAwareResourceBundleMessageSource) {
            basenames = ((baseNames as List)+ (i18nNames as List)).toArray()
            fallbackToSystemLocale = false
            pluginManager = manager
            grailsApplication = application
        }
    }
    def doWithDynamicMethods = { ctx ->
        println "doWithDynamicMethods"
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        println "doWithApplicationContext"
        def i18nNames = LocalConfigUtil.checkI18nNames(application)
        def messageSource = applicationContext.getBean("messageSource")
        if (messageSource) {
            messageSource.basenames = ((baseNames as List)+ (i18nNames as List)).toArray()
            messageSource.clearCache() 
            messageSource.clearCacheIncludingAncestors()
            println messageSource.getMessage('a',null,Locale.CHINA) 
            println "messageSource:${messageSource}"
        }
        def webappFiles = LocalConfigUtil.checkWebappFiles(application)
        def servletContext = application.getParentContext().getServletContext()
        def startStr = "web-app"
        webappFiles.each {from->
            int idx = from.absolutePath.indexOf(startStr)+startStr.size()
            def resPath = from.absolutePath[idx..-1]
            def to = new File(servletContext.getRealPath(resPath))
            if (from.lastModified() != to.lastModified()) {
                FileUtils.copyFile(from,to,true)
            }
        }
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

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
