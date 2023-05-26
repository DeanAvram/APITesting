package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class PutRequest {

    public Response updateBooking(JSONObject booking, String bookingId) {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        return ((Response) given()
                .header("Content-type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .and().body(booking)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .extract());
    }
}
