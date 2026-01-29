package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public class BaseTest {


  protected static final Logger logger =LogManager.getLogger(BaseTest.class);
    @BeforeSuite
    public void beforeSuite(){

          String executionType=      ConfigReader.get("executionType");

    }


    protected void info(String message){

        logger.info(message);

    }

    protected void warn(String msg){
        logger.warn(msg);
    }

    protected void error(String msg){
        logger.error(msg);
    }



}
