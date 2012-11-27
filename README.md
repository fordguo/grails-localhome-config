##  Introduce
When your web app production deploy into more than one scenes,there will be many customized contents,such as customer logo,local configration or the i18n code label.
With localhome-custom plugin,you can customize the local customer logo(the default in web-app directory),local i18n properties,and add the localhome configure file into *grails.config.locations* 

## Customed Contents
All the customized content will be put into local user home directory
* For Unix/Linux: ~/.grails/appName
* For window: *Documents and Settings\\User\\.grails\\appName*

### I18n properties
The i18n files will be in local user home's *grails-app/i18n* directory,the plugins will add the home i18n properties into the **messageSource** bean.

Note:**the properties should be encoded as UTF-8**

### Web-app resources
The customized resources (images,icon,css etc) will be in local user home's *web-app/*,so if you wanna customize the customer's theme,icon,logo ,you can put the customer's resouces into the *web-app* directory.