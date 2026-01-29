package middleWare;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import pojo.CreateUserPayload;
import utilities.CommonRestUtils;
import utilities.JsonUtils;

import java.io.IOException;
import java.util.Map;

public class CreateUser extends CommonRestUtils {




    private String endPoint= System.getProperty("createUserEndPoint");
    private String baseuri= System.getProperty("reqResBaseUri");

private Response response;
    public Response createUserRequest(String userName,String jobTitle) throws IOException {

       return request(baseuri,endPoint,null,null,getUserPayload(userName,jobTitle), ContentType.JSON, Method.POST,null);

    }


    public Map<String,Object> getUserPayload(String userName,String jobTitle){

        return Map.of("username",userName,"job",jobTitle);

    }
// this work for small payloads and dynamic fields
    public CreateUserPayload getUserPayloadUsingPojo(String userName){

      CreateUserPayload payload=new CreateUserPayload();
     payload.setUsername(userName);
     return payload;


    }

    // If we want to set any values.This works well for large payloads as well
    public CreateUserPayload getPayloadUsingJson(String userName) throws JsonProcessingException {

        String jsonString = JsonUtils.readJsonFile(
                "src/test/resources/payloads/createUser.json");

        CreateUserPayload createUserRequest =
                parseResponseClass(jsonString, CreateUserPayload.class);


        //This uses Gson/Jackson to:
//Read all values from the JSON file
//Populate them into CreateUserPayload
        createUserRequest.setUsername(userName);
        return createUserRequest;
    }




    public boolean verifyUserCreated(){

        return (response.statusCode()==201);
    }



    public String getResponse(){

       return  "Response code is "+response.statusCode() + "\n Response body: "+response.getBody().asString();
    }


}
