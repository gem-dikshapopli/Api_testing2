package implementation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class DemoImplementation {


    public static Properties properties = new Properties();
    public static String apiUrl;
    public static String authentication;

    //----For Reading and Fetching data from properties file
    public static void properties(){
        try (InputStream inputStream = new FileInputStream("config.properties")) {
            properties.load(inputStream);

            // Access the URL and string by their keys
             apiUrl = properties.getProperty("url");
            authentication = properties.getProperty("auth");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String bookingId;
   static Logger log = Logger.getLogger(String.valueOf(DemoImplementation.class));
    public static void getMethod() {
        log.info("*****************************GET METHOD**************************");
        try {

            RequestSpecification request = RestAssured.given()
                    .baseUri(apiUrl)
                    .log()
                    .all()
                    .contentType(ContentType.JSON);


            Response response = request
                    .get()
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response();

            //To match if the status code is 200 or not if not otherwise it will stop the execution

            int status = response.getStatusCode();
            Assert.assertEquals(200, status);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void postMethod()
    {
        log.info("**************************POST METHOD**************************");

        try {

                RequestSpecification requestSpecification = RestAssured
                        .given()
                        .baseUri(apiUrl)
                        .contentType(ContentType.JSON);

                JSONObject payload = new JSONObject();
                payload.put("firstname", "Diksha");
                payload.put("lastname", "popli");
                payload.put("totalprice", 122);
                payload.put("depositpaid", true);

                //--Nested JSON Object
                JSONObject innerPayLoad = new JSONObject();
                innerPayLoad.put("checkin", "2018-01-01");
                innerPayLoad.put("checkout", "2019-01-01");
                payload.put("bookingdates", innerPayLoad);
                payload.put("additionalneeds", "bowls");


                Response response = requestSpecification
                        .body(payload.toString())
                        .post()
                        .then()
                        .extract()
                        .response();
                String string = response.prettyPrint();

                //----To Get that Specific id where we are going to perform patch put and delete method

                String finalString = "";//----To store responseBody as String which doest not contain "{" and "}"
                for (int i = 0; i < string.length(); i++) {
                    if (!(string.charAt(i) == '{' || string.charAt(i) == '}')) {
                        finalString = finalString.concat("" + string.charAt(i));
                    }
                }

                //----Split the finalString where we found ":" and ","
                String[] splitFinalString = finalString.split("[:]+|[,]+");

                //----at 1st index we are going to have our bookingId
                bookingId = "" + splitFinalString[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

//    -----------------Patch method to change firstname and lastname
    public static void patchMethodForBooking() {
        log.info("*************************PATCH METHOD**********************");
        try {

            RequestSpecification requestForPatch = RestAssured
                    .given()
                    .baseUri(apiUrl)
                    .contentType(ContentType.JSON)
                    .header("Authorization", authentication)
                    .header("Accept", "application/json");


            JSONObject payloadForPatch = new JSONObject();
            payloadForPatch.put("firstname", "changed");//The Changed Payload


            Response responseForPatch = requestForPatch
                    .body(payloadForPatch.toString())
                    .patch(bookingId)
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response();

            //----------To Print the Response Body
            responseForPatch.prettyPrint();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void putMethod() {
        log.info("********************PUT Method***********************");

        try {

            RequestSpecification requestForPut = RestAssured
                    .given()
                    .baseUri(apiUrl)
                    .contentType(ContentType.JSON)
                    .header("Authorization", authentication)
                    .header("Accept", "application/json");


            JSONObject payloadForPut = new JSONObject();
            payloadForPut.put("firstname", "chng");
            payloadForPut.put("lastname", "chng");
            payloadForPut.put("totalprice", 1267);
            payloadForPut.put("depositpaid", true);
            JSONObject innerPayLoadForPut = new JSONObject();
            innerPayLoadForPut.put("checkin", "2018-01-01");
            innerPayLoadForPut.put("checkout", "2019-01-01");
            payloadForPut.put("bookingdates", innerPayLoadForPut);
            payloadForPut.put("additionalneeds", "bowls");

            Response responseForPut = requestForPut
                    .body(payloadForPut.toString())
                    .put(bookingId)
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response();
            responseForPut.prettyPrint();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteMethodForBooking() {
        log.info("***********************DELETE METHOD***********************");
        try {
            RequestSpecification requestForDelete = RestAssured
                    .given()
                    .baseUri(apiUrl)
                    .contentType(ContentType.JSON)
                    .header("Authorization", authentication)
                    .header("Accept", "application/json");

            requestForDelete
                    .delete(bookingId)
                    .then()
                    .extract()
                    .response();

            //----GET we will get the Status Code as 404 which will give us not found
            requestForDelete
                    .get(bookingId)
                    .then()
                    .log()
                    .all()
                    .extract()
                    .response();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
