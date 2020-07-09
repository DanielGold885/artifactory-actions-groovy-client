package ArtifactoryRestActions

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.6')

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import groovy.json.JsonSlurper
import groovy.json.JsonException


//Artifactory credentials and base URL
String baseUrl = "http://10.70.30.83:8082"
String user = "user1"
String password = "password"

//Fields declaration
def resp
def jsonFile
def jsonSlurper
def jsonObject


def rt = new RESTClient(baseUrl)
rt.auth.basic user, password
rt.contentType = ContentType.JSON


try {
    String artifactJsonPath = "../Files/uploadArtifact.json"
    jsonFile = new File(artifactJsonPath)
    jsonSlurper = new JsonSlurper()
    jsonObject = jsonSlurper.parse(jsonFile)
}
catch (JsonException je){
    println(je)
    System.exit(1)
}
catch (HttpResponseException hre){
    println(hre)
    System.exit(1)
}
catch (Exception e){
    println(e)
    System.exit(1)
}


try {
    resp = rt.put(
            path: '/artifactory/npm-local/text1.txt',
            body: jsonObject,
            requestContentType: ContentType.JSON)
}

catch (HttpResponseException hre) {
    println("Failed to Upload. Reason: \n \n$hre")
    System.exit(1)
}
catch (Exception e){
    println(e)
    System.exit(1)
}

assert resp.status == 201


