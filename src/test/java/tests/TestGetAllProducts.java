package tests;

import objects.ManageApiObjects;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.BaseTest;

import java.io.IOException;

public class TestGetAllProducts extends BaseTest {





private ManageApiObjects manageApiObjects;


    @BeforeClass
    public void beforeClass(){
manageApiObjects=new ManageApiObjects();
    }

    @Test
    public void verifyAllProducts() {
        info("Getting all the products ");
        manageApiObjects.getAllProducts().getAllProducts();

        info("Verifying products exists");
        manageApiObjects.getAllProducts().verifyProductsExists();
        info("Verified products exists");

        info("Verifying product structure");
        manageApiObjects.getAllProducts().verifyProductStructure();
        info("Product structure is verified");

        info("Verifying all keys in the products list");
        manageApiObjects.getAllProducts().verifyProductContainsValue();
        info("Products list verified successfully");
    }
}
