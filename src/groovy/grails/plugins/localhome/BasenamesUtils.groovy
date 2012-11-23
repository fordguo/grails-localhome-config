package grails.plugins.localhome

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ContextResource

import grails.util.BuildSettingsHolder
import org.apache.commons.lang.StringUtils

class BasenamesUtils {
    static String baseDir = "grails-app/i18n"
    //Code from  2.1.x grails-plugin-i18n/src/main/groovy/org/codehaus/groovy/grails/plugins/i18n/I18nGrailsPlugin.groovy
    static def i18nCode(messageResources,baseNames) {
        for (resource in messageResources) {
            def isInlineResource = false
            try {
                isInlineResource = (resource instanceof ClassPathResource) ? false :
                    BuildSettingsHolder?.settings?.isInlinePluginLocation(new File(resource.file.getParent().minus("/grails-app/i18n")))
            } catch(e) {
                // Ignore the failed getFile/getRealPath on a non exploded resource. 
            }
            String path
            if (isInlineResource) {
                path = resource.file.path
            } else {
                if (resource instanceof ContextResource) {
                    path = StringUtils.substringAfter(resource.pathWithinContext, baseDir)
                } else {
                    path = StringUtils.substringAfter(resource.path, baseDir)
                }
            }
            String fileName = resource.filename
            int firstUnderscore = fileName.indexOf('_')
            if (firstUnderscore > 0) {
                int numberOfCharsToRemove = fileName.length() - firstUnderscore
                int lastCharacterToRetain = -1 * (numberOfCharsToRemove + 1)
                path = path[0..lastCharacterToRetain]
            }
            else {
                path -= ".properties"
            }
            baseNames << (isInlineResource ? path : "WEB-INF/" + baseDir + path)
        }
    }
    
    protected static def getBasename(filename) {
        int firstUnderscore = filename.indexOf('_')
        if (firstUnderscore > 0) {
            int numberOfCharsToRemove = filename.length() - firstUnderscore
            int lastCharacterToRetain = -1 * (numberOfCharsToRemove + 1)
            return filename[0..lastCharacterToRetain]
        }else {
            return filename - ".properties"
        }
    }
}
