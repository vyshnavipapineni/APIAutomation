package tests;

import objects.ManageApiObjects;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;

public class TestUserListApi extends BaseTest {



    ManageApiObjects manageApiObjects;
    @BeforeClass
    public void beforeClass(){
        manageApiObjects= new ManageApiObjects();
    }



    @Test
    public void verifyAndCreateUser() throws IOException {
        String name="Vyshnavi Papineni";
String title="leader";
        boolean userCreated;
        boolean userPresent;

        info("Verifying if user is present by requesting users api");

     userPresent=  manageApiObjects.getAllUsers().verifyUserPresent(name);

     info((userPresent)?"user is present in the system": "user not present in the system. creating a new user, current list: \n"+manageApiObjects.getAllUsers().getAllnames());


     if(!userPresent)
         info("creating a new user");
     manageApiObjects.createUser().createUserRequest(name,title);
     info("verifying user created");
        userCreated=  manageApiObjects.createUser().verifyUserCreated();
        Assert.assertTrue(userCreated,"Failed to create user.Response is: "+manageApiObjects.createUser().getResponse());
        }

    }

