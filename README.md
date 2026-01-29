🧪**API Automation Framework**

This is a comprehensive API automation testing framework built with Java and TestNG. It leverages RestAssured for API testing and is designed with a clean, modular structure for scalability and maintainability.

**🚀 What This Project Covers**

✅ API testing using RestAssured
✅ Supports all types of authentication (Bearer, Basic, OAuth, etc.)
✅ Token generation and management handled via reusable utilities
✅ Configurable through api.properties and JSON payloads
✅ Reusable POJOs, API object managers, and utilities
✅ Centralized logging for request, response, and assertion details


**🛠 Tools & Technologies**

Java 11+
RestAssured
TestNG
Jackson (for JSON parsing)
Maven
Git / GitHub

🧱 **Project Structure**
src/
 ├── main/
 │    └── java/
 │         ├── middleWare/      # API request classes (e.g., GetAllProducts, SearchProducts, CreateUser)
 │         ├── pojo/            # POJOs for request and response payloads (Products, Category, UserType)
 │         ├── objects/         # API object managers (ManageApiObjects)
 │         └── utilities/       # Reusable utilities
 │              ├── CommonRestUtils.java  # Handles all request methods, auth types, and token generation
 │              ├── ConfigReader.java     # Reads API configuration from api.properties
 │              ├── JsonUtils.java        # JSON parsing helper methods
 │              ├── OAuthTokenUtils.java # OAuth token generation
 │              └── TokenUtils.java      # Token management
 └── test/
      └── java/
           ├── tests/           # Test classes (TestGetAllProducts, TestSearchProducts, TestUserListApi)


🧪** How to Run**

**Prerequisites**
Java 11 or higher
Maven
TestNG
Run All Tests

**Right-click apiSanity.xml → Run as TestNG Suite**

🔸 **Sample Test Data**
Example for user creation payload (CreateUserPayload):

**{
  "name": "API Automation User",
  "gender": "female",
  "email": "api.user+test@domain.com",
  "status": "active"
}
**

📊 **Reporting**
.All API requests, responses, and assertion logs are captured

.Detailed execution logs can be viewed via console or custom log files

.Framework is structured to easily integrate with reporting tools (e.g., ExtentReports)

**✅ Key Highlights**

Handles multiple authentication types seamlessly

Centralized request methods and response validation

Modular and reusable design for scalability

Supports parallel execution with TestNG

Easy maintenance and addition of new API endpoints
