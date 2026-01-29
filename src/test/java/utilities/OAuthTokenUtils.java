package utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OAuthTokenUtils {

    private static String token;
    private static long expiry_time;

    //public  static <T> String getToken(String endpoint, T payload)
    public  static String getToken(String endpoint, Object payload){
        if(token==null || isExpired()){
            token=  generateToken(endpoint,payload);
        }
        return token;
    }



    public static String generateToken(String endPoint, Object payload){

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payload).post(endPoint);


        token=   response.jsonPath().getString("access_token")!=null?response.jsonPath().getString("access_token"):
                response.jsonPath().getString("token");

        if(token==null ) throw new RuntimeException("Token is null");
        int expires_in= response.jsonPath().getInt("expires_in");
        // 60 seconds= 1 hour
        expiry_time= System.currentTimeMillis() + (expires_in * 1000);
        // current time(11 am) + (60*1000=6000ms) 11.am+1=12 pm

        return token;
    }


    public static boolean  isExpired(){

        return System.currentTimeMillis()> expiry_time;

    }
}
