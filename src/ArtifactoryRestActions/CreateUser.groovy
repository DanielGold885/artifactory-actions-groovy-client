package ArtifactoryRestActions

@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.6')

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import groovyx.net.http.ResponseParseException
import groovy.json.JsonException
import groovy.json.JsonSlurper

//Artifactory credentials and base URL
String baseUrl = "<artifactoryURL>"
String user = "<yourUser>"
String password = "<YourPassword>"

//Resources
String userJsonFilePath = "../Files/user.json"

//Fields declaration
def jsonFile
def resp
def jsonObject
def rt
def jsonSlurper


try {
    rt = new RESTClient(baseUrl)
    rt.auth.basic user, password
    rt.contentType = ContentType.JSON
}
catch(MissingPropertyException mpe){
    println("Bla $mpe")
}
catch (Exception e){
    println(e)
    System.exit(1)
}

try {
    jsonSlurper = new JsonSlurper()
    jsonFile = new File(userJsonFilePath)
    jsonObject = jsonSlurper.parse(jsonFile)
}
catch (JsonException je) {
    println("Create user failed: $je" )
    System.exit(1)
}
catch (Exception e){
    println(e)
    System.exit(1)
}

try {
    String createUserPath = "/artifactory/api/security/users/"
    String newUser = "user1"

    resp = rt.put(
            path: createUserPath.concat(newUser),
            body: jsonObject,
            requestContentType: ContentType.JSON)
}
catch (ResponseParseException rpe) {
    println("Create User Failed. Reason: $rpe")
    System.exit(1)
}
catch (Exception e) {
    println("Create user failed: $e" )
    System.exit(1)
}

try {
    assert resp.status == 201
    println(resp.status)
}
catch (ResponseParseException rpe){
    println("Cannot read response. Reason: " + rpe)
    System.exit(1)
}
catch (Exception e){
    println(e)
    System.exit(1)
}
