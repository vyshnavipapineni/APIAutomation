package utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonUtils {



    public static   String readJsonFile(String filepath){

        try {
//Converts the file path String into a Path object
            Path path = Paths.get(filepath);
//1. Opens the file 2.Reads entire file content 3.Converts it into a String
           return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read json file" + filepath,e);
        }

    }
}


//checked exceptions: IOException, JsonException
//✅ Checked Exceptions
//
//These are checked at compile time.
//Java forces you to handle them using try-catch or declare them with throws.
//
//Examples:
//
//IOException
//SQLException
//ClassNotFoundException
//FileNotFoundException
//JsonProcessingException   // Jackson
//InterruptedException
//--------------------------------------------------------------------
//unchecked exceptions: RunTime exception, Null pointer Exception,IllegalArgumentException
//✅ Unchecked Exceptions
//
//These occur at runtime, and Java does not force you to handle them.
//
//Examples:
//
//RuntimeException
//NullPointerException
//IllegalArgumentException
//ArrayIndexOutOfBoundsException
//ArithmeticException
//NumberFormatException
//ClassCastException





//// Option 1: catch it
//try {
//        Files.readString(path);
//} catch (IOException e) {
//        e.printStackTrace();
//}
//
//// Option 2: declare it
//public void readFile() throws IOException {
//    Files.readString(path);
//}


//-------------------------------------------------------------------------------------------------------

//4️⃣ Example: Custom Checked Exception
//public class ProductNotFoundException extends Exception {
//    public ProductNotFoundException(String message) {
//        super(message);
//    }
//}
//
//


//Usage:
//public Product getProduct(int id) throws ProductNotFoundException {
//    if (id <= 0) {
//        throw new ProductNotFoundException("Product not found for id: " + id);
//    }
//    return product;
//}