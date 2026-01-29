package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {


    private static Properties properties= new Properties();

    static {

        try(InputStream is=ConfigReader.class.getClassLoader().getResourceAsStream("api.properties")){

            if(is==null) throw new RuntimeException("api.properties file is not found");
            properties.load(is);

            for(String name:properties.stringPropertyNames()){

                if(System.getProperty(name)==null){

                  System.setProperty(name,properties.getProperty(name));
                }
            }



        } catch (IOException e) {
            throw new RuntimeException("api.properties is not loaded properly",e);
        }
    }



    public static  String get(String key){
     return    properties.getProperty(key);
    }
}
