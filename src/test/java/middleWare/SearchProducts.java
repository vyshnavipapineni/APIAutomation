package middleWare;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.testng.Assert;
import utilities.CommonRestUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class SearchProducts extends CommonRestUtils {
    private Response response;

private String baseUri=System.getProperty("getAllProductsBaseUri");
private String endPoint=System.getProperty("searchProductsEndPoint");

    public Response searchProducts(String productType) {
        return response = request(baseUri, endPoint, null, null, getPayload(productType), ContentType.URLENC, Method.POST,null);
    }

    public Map<String,Object> getPayload(String product){
        return  Map.of("productName",product);

    }

    public Response searchProductUsingMultipart(String productName, File file){
        if (file == null || !file.exists()) {
            throw new RuntimeException("File not found!");
        }


        Map<String, Object> formParams = Map.of("search_product", productName);

        Map<String, Object> files = Map.of("file", file);
//mutipart accepts both text and file
response=mutipart(baseUri,endPoint,null,formParams,file,files, Method.POST);
return response;

    }

    public void verifySearchProductResponse(){

        Assert.assertEquals(200,response.statusCode()==200);
    }


    public void verifySearchProduct(String productName){

        List<Map<String,Object>> productList=response.jsonPath().getList("products");


        Assert.assertTrue(productList.size()>0,"product list is empty");


      boolean isProductFound=  productList.stream().anyMatch(product->product.get("productName")!=null && product.get("productName").toString().equalsIgnoreCase(productName));
        Assert.assertTrue(isProductFound, "Product not found in the search results");
    }
}
