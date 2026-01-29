package tests;

import objects.ManageApiObjects;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.BaseTest;

public class TestSearchProducts extends BaseTest {

    private ManageApiObjects manageApiObjects;


    @BeforeClass
    public void beforeClass(){
        manageApiObjects= new ManageApiObjects();
    }



    @Test

    public void searchProduct(){
info("Searching the product");

manageApiObjects.searchProducts().searchProducts("top");
info("Verifying the product response");
manageApiObjects.searchProducts().verifySearchProductResponse();
info("Verifying the search product");
manageApiObjects.searchProducts().verifySearchProduct("top");
info("Serach product is verified");

    }
}
