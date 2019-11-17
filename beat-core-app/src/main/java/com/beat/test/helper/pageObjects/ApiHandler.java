package com.beat.test.helper.pageObjects;

import com.beat.test.helper.ConfigProperties;
import com.beat.test.helper.Logger;
import com.beat.test.helper.dto.Employee;
import com.beat.test.helper.dto.EmployeePostBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.HttpClientConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;

public class ApiHandler {

    Logger logger;
    public String url;
    private static String employeesRoute = "/employees";
    private static String employeeRoute = "/employee/";
    private static String createRoute = "/create";
    private static String updateRoute = "/update/";
    private static String deleteRoute = "/delete/";

    RestAssuredConfig waitConfig = RestAssured.config()
        .httpClient(HttpClientConfig.httpClientConfig()
        .setParam("http.connection.timeout", 10000)
        .setParam("http.socket.timeout", 10000));

    public enum API {
        EMPLOYEES(employeesRoute),
        EMPLOYEE(employeeRoute),
        CREATE(createRoute),
        UPDATE(updateRoute),
        DELETE(deleteRoute);

        private   String myRoute;
        API(String route){
            myRoute = route;
        }
        public String get(){
            return myRoute;
        }
    }

    public ApiHandler() {
        logger = new Logger();
        ConfigProperties configProperties = new ConfigProperties();
        url = configProperties.getPropertyValue(ConfigProperties.APPLICATION_URL);
    }
    public Assertions assertions() {return new Assertions(); }

    /*
     * Create a custom URL based on the available routes and params
     */
    public String createURL(API route, String params) {
        String urlString = url + route.get();
        if (params != null) {
            urlString += params;
        }
        logger.info("Created url connection string: " + urlString);
        return urlString;
    }

    public Response getRequest(String endpoint) {
        return  given().config(waitConfig).headers("Content-Type", ContentType.HTML, "Accept", ContentType.JSON)
                .when().get(endpoint)
                .then().contentType(ContentType.HTML).extract().response();
    }

    public void deleteEmployee(Employee employee) {
        String endpoint = createURL(API.DELETE, employee.getId());
        JSONObject jsonObject = new JSONObject(given().config(waitConfig).contentType(ContentType.JSON).delete(endpoint).htmlPath().getString("html.body"));
        if (jsonObject.get("success").toString().contains("successfully")) {
            Logger.pass("Employee with name : '" + employee.getEmployee_name() + "' deleted");
        }
    }

    public Employee createEmployee(String requestBody) throws IOException {
        String endpoint = createURL(ApiHandler.API.CREATE, "");
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(given().config(waitConfig).contentType(ContentType.JSON).body(requestBody).post(endpoint).htmlPath().getString("html.body"));
        Logger.info("Created employee with id: '" + jsonObject.get("id") +"'");
        return EmployeePostBody.castToEmployee(objectMapper.readValue(jsonObject.toString(), EmployeePostBody.class), null);
    }

    public void updateEmployee(Employee employee, String requestBody ) throws IOException {
        String endpoint = createURL(API.UPDATE, employee.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = new JSONObject(given().config(waitConfig).contentType(ContentType.JSON).body(requestBody).put(endpoint).htmlPath().getString("html.body"));
        Logger.info("Updated user  with name: " + jsonObject.get("name"));
    }

    /**
     Get a single employee from the relevant endpoint using the user id
     */
    public Employee getEmployeeById(String userID) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String endpoint = createURL(ApiHandler.API.EMPLOYEE,userID);
        JSONObject jsonObject = new JSONObject(getRequest(endpoint).htmlPath().getString("html.body"));
        Logger.info("Found employee '" + jsonObject.toString() + "'");
        return objectMapper.readValue(jsonObject.toString(), Employee.class);
    }

    /**
     Get the employees from the relevant endpoint as a List
     */
    public List<Employee> getEmployees() throws IOException {
        List<Employee> usersList = new ArrayList<>();
        String endpoint = createURL(ApiHandler.API.EMPLOYEES,"");
        JSONArray jsonArray = new JSONArray(getRequest(endpoint).htmlPath().getString("html.body"));

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ObjectMapper objectMapper = new ObjectMapper();
            usersList.add(objectMapper.readValue(jsonObject.toString(), Employee.class));
        }
        return usersList;
    }

    /**
     * As improvement, the below method can be moved outside
     * the ApiHandler to a general operations business class
     * @param min
     * @param max
     * @return
     */
    public String generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return String.valueOf(r.nextInt((max - min) + 1) + min);
    }

}
