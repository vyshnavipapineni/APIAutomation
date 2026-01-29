package utilities;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CommonRestUtils {


    public enum AuthType {
        NONE,
        BEARER,
        BASIC,
        API_KEY,
        JWT,
        OAUTH
    }

    private Response response;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Gson gson;
    public CommonRestUtils(){
        gson= new Gson();
    }


    public Response request(String baseuri, String endPoint, Map<String, Object> headers, Map<String, Object> queryParms, Object payload, ContentType contentType, Method method, AuthType authType) {

        RequestSpecification requestSpecification = RestAssured.given();
        if (baseuri != null & endPoint != null) requestSpecification.baseUri(baseuri).basePath(endPoint);
        if (headers != null) requestSpecification.headers(headers);

        if (queryParms != null) requestSpecification.queryParams(queryParms);

        switch (authType){
            case BEARER -> applyBearerToken(requestSpecification);
            case BASIC -> applyBasicToken(requestSpecification);
            case OAUTH -> applyOauthToken(requestSpecification);
            case JWT -> applyJwttoken(requestSpecification);
            case API_KEY -> appApiKey(requestSpecification);
        }

        if (payload != null) {
            if (contentType.equals(ContentType.JSON)) requestSpecification.body(payload);
            else if (contentType.equals(ContentType.URLENC)) requestSpecification.formParams((Map<String, ?>) payload);

            else if (contentType.equals(ContentType.MULTIPART)) requestSpecification.multiPart((File) payload);
        }

        if (contentType != null) requestSpecification.contentType(contentType);

        response = requestSpecification.log().all().request(method);

        return response;
    }



    public void appApiKey(RequestSpecification requestSpecification){
        requestSpecification.header("x-api-key", ConfigReader.get("api.key"));
        //or
        requestSpecification.queryParam("api_key", ConfigReader.get("api.key"));

    }
    public void applyJwttoken(RequestSpecification requestSpecification){

        String filePath="src/test/resources/payloads/jwtRequest.json";
        requestSpecification.headers("Authorization","Bearer "+TokenUtils.generateToken(ConfigReader.get("jwt.endpoint"),filePath));
    }
    public void applyOauthToken(RequestSpecification requestSpecification){

        String tokenEndpoint = System.getProperty("token.endpoint");
String clientId=System.getenv("client_Id");
String clientSecret= System.getProperty("client_Secret");

        //RestAssured will automatically convert the Map into JSON like:
        //RestAssured automatically serializes the Map into JSON, like:
        //👉 Important:
        //The payload is still a Map in Java, not a String.
        //RestAssured converts it to JSON only at request time.
        Map<String, String> payload = Map.of(
                "client_Id", clientId,
                "client_Secret", clientSecret
        );




        String token= OAuthTokenUtils.getToken(tokenEndpoint,payload);

        requestSpecification.headers("Authorization","Bearer "+token);

    }

    private void applyBearerToken(RequestSpecification request) {


        String token = TokenUtils.getToken(ConfigReader.get("token.endpoint"),"src/test/resources/payloads/tokenRequest.json");
        request.header("Authorization", "Bearer " + token);
    }


    public void applyBasicToken(RequestSpecification requestSpecification){
        //preemptive().basic() → sends the Authorization header immediately
        //Without preemptive() → waits for a 401 Unauthorized challenge first
        requestSpecification.auth().preemptive().basic(ConfigReader.get("basic.username"),ConfigReader.get("basic.password"));
    }





    public Response mutipart(String baseuri, String endpoint, Map<String, ?> headers, Map<String, ?> formPrms, File files, Map<String, Object> mutipart, Method method) {

        RequestSpecification request = RestAssured.given()
                .baseUri(baseuri)
                .basePath(endpoint);
        if (headers != null) request.headers(headers);

        if (formPrms != null) request.formParams(formPrms);
// if mutipart is file
        if (files != null) request.multiPart(files);

        // if mutipart is map
        if (mutipart != null) {

            for (Map.Entry<String, Object> entry : mutipart.entrySet()) {

                request.multiPart(entry.getKey(), entry.getValue());
            }
        }

// what if multipart has both text and file?

            Map<String, Object> mutiparts = new HashMap<>();
            mutipart.put("description", "Profile picture"); // text
            mutipart.put("image", new File("path/to/pic.jpg")); // file
if(mutipart!=null){
            for (Map.Entry<String, Object> entry : mutipart.entrySet()) {

                request.multiPart(entry.getKey(), entry.getValue());
            }
        }


        return request.request(method);
    }





    public Object getJsonValue(String response, String jsonpath){

       return JsonPath.read(response,jsonpath);

    }

    // using gson is simpler and lightweight
    public Object parserResponseIntoClass(String responseBody, Class responseClass){
        return gson.fromJson(responseBody, responseClass);
    }

    //This is using jacksonBinder

    //JsonProcessingException is a checked exception.

    // Java rule:

    // //If a method calls another method that throws a checked exception, you must:

          //  catch it OR

   // declare it using throws
    public <T> T parseResponseClass(String response, Class<T> responseClass) throws JsonProcessingException {


      return   objectMapper.readValue(response,responseClass);

    }

    public <T> T parseResponseUsingUnchecked(String response, Class<T> responseClass) {
        try {
            return objectMapper.readValue(response, responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse response", e);
        }
    }




    public long getResponseTime(){

        if(response!=null){
            return response.timeIn(TimeUnit.SECONDS);
        }

        else throw new NullPointerException("Response is null");
    }

}










