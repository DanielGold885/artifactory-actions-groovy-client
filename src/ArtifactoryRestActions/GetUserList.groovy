package ArtifactoryRestActions

import groovy.json.JsonBuilder
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.6')

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient


//Artifactory credentials and base URL
String baseUrl = "<artifactoryURL>"
String user = "<yourUser>"
String password = "<YourPassword>"


// Routs
String getUsersPath = '/artifactory/api/security/users'


def rt = new RESTClient(baseUrl)
rt.auth.basic user, password
rt.contentType = ContentType.JSON

try {
        rt.get(path: getUsersPath) { response, json ->
        println(response.status)
        println(json.name[8])
        def outputJson = new JsonBuilder(json).toPrettyString()
        println(outputJson)
    }
}
catch (HttpResponseException hre){
    println(hre)
}
catch (Exception e){
    println(e)
}

