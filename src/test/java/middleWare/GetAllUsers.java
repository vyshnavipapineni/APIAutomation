package middleWare;

import io.restassured.http.Method;
import io.restassured.response.Response;
import utilities.CommonRestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAllUsers extends CommonRestUtils {


private List<String> userList = new ArrayList<>();
    private String baseUri=System.getProperty("reqResBaseUri");
    private String endPoint= System.getProperty("getAllUsersEndPoint");
    public Response getAllUsers(int pageCount) throws IOException {

       return request(baseUri,endPoint,null,getQueryParams(pageCount),null,null, Method.GET,null);
    }


    public Map<String,Object> getQueryParams(int pageCount){

        return Map.of("page",pageCount);

    }


    public boolean verifyUserPresent(String userName) throws IOException {
        List<Response> responses= new ArrayList();

        int pageCount=1;
   Response   response=  getAllUsers(pageCount);

      responses.add(response);
     int totalPages= getTotalPages(response);

     while(pageCount<totalPages) {

         response = getAllUsers(++pageCount);
         responses.add(response);
     }
       return checkUserNameInResponse(userName,responses);
     }


     public boolean checkUserNameInResponse(String userName,List<Response> responses){

     List<String> names=  getNames(getFirstNamesList(responses), getLastNamesList(responses));


     return verifyUserNameExistsInList(names,userName);
     }

     public boolean verifyUserNameExistsInList(List<String> names,String userName){


        for(String name:names){
            return name.equalsIgnoreCase(userName);
        }
        return false;
     }

     public List<String> getNames(List<List<String>> firstNameList, List<List<String>> lastNameList){


        List<String> firstnames= new ArrayList<>();
        List<String> lastNames = new ArrayList<>();


        for(List<String> fNames:firstNameList){
firstnames.addAll(firstnames);
        }

        for(List<String> lNames: lastNameList){

            lastNames.addAll(lNames);
        }

        for(int counter=0; counter<firstnames.size();counter++){
          userList.add(firstnames.get(0)+" "+lastNames.get(0));
        }
        return userList;
     }


     public List<List<String>> getFirstNamesList(List<Response> responses){


        List<List<String>> firstnameList = new ArrayList<>();

        for(Response response:responses){

            firstnameList.add(getFirstNameFromResponse(response));
        }
return firstnameList;
     }

    public List<List<String>> getLastNamesList(List<Response> responses){


        List<List<String>> lastnameList = new ArrayList<>();

        for(Response response:responses){

            lastnameList.add(getFirstNameFromResponse(response));
        }
        return lastnameList;
    }

     public List<String> getFirstNameFromResponse(Response response){


       return  (List<String>)getJsonValue(response.asString(),"$.data[*].first_name");
     }




    public int getTotalPages(Response response){


     int totalPages=  ((Number) getJsonValue(response.asString(),"$.totalpages")).intValue();
        return totalPages;
    }


public List<String> getAllnames(){
        return userList;
}

}
