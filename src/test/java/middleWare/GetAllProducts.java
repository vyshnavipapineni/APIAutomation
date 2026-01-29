package middleWare;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.AllProducts;
import pojo.Products;
import utilities.CommonRestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetAllProducts extends CommonRestUtils {

    private Response response;

  String baseUri=  System.getProperty("getAllProductsBaseUri");
    String endPoint=  System.getProperty("getAllProductsEndPoint");



    public Response getAllProducts() {
      response=  request(baseUri,endPoint,null,null,null,null, Method.GET,null);
    return response;
    }

    public void verifyProductsExists(){

     int size=   ((List<Map<String,Object>>) getJsonValue(response.asString(),"$.products[*]")).size();
        Assert.assertTrue(size>0);
    }


    public void verifySpecificProductExists(String productName) throws JsonProcessingException {
        List<Map<String, Object>> listOfProducts = (List<Map<String, Object>>) getJsonValue(response.asString(), "$.products[*]");

boolean found=listOfProducts.stream().anyMatch(p->(String.valueOf(p.get("name")).equalsIgnoreCase(productName)));
//for integer
        listOfProducts.stream().anyMatch(p->Integer.parseInt(String.valueOf(p.get("id") ))==1);


        Assert.assertTrue(found);
    }


    public void verifyProductName(String name){
       List<String> productName= (List<String>) getJsonValue(response.asString(),"$.products[*].name");
     Assert.assertTrue(productName.contains(name));
    }


    public void verifyProductStructure(){


        Map<String, Object> productDetails = (Map<String, Object>) getJsonValue(response.asString(), "$.products[0]");


      Assert.assertTrue(productDetails.containsKey("id"));

      Assert.assertTrue(productDetails.containsKey("name"));
        Assert.assertTrue(productDetails.containsKey("price"));

        Assert.assertTrue(productDetails.containsKey("brand"));


        Map<String, Object> category = (Map<String, Object>) productDetails.get("category");

Assert.assertTrue(category.containsKey("category"));

        Map<String, Object> usertype = (Map<String, Object>) category.get("usertype");
        Assert.assertTrue(usertype.containsKey("usertype"));
    }


    public void verifyProductContainsValueUsingChecked() {
        try{
            AllProducts allProducts =  parseResponseClass(response.asString(), AllProducts.class);
        }
        catch (JsonProcessingException e){
        e.printStackTrace();

        }
    }
    
    public void verifyProductContainsValue() {
        AllProducts allProducts = parseResponseUsingUnchecked(response.asString(), AllProducts.class);

      Assert.assertFalse(allProducts.getProducts().isEmpty());

      for(Products products:allProducts.getProducts()){

          Assert.assertNotNull(products.getCategory(),"Category is null");
Assert.assertNotNull(products.getCategory().getUsertype(),"Usertype is missing");
          Assert.assertNotNull(products.getCategory().getUsertype().getUsertype(),"Usertype  value is null");



      }
    }


    public void verifyResponseTime(){
 Assert.assertTrue (getResponseTime() <=3,"Response Time is greater than 3");
    }


}
