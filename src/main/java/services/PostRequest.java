package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class PostRequest {

	protected JSONArray orders;

	public Response createBooking(JSONObject booking) {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
			Response response = (Response) given()
					.header("Content-type", "application/json")
					.and().body(booking)
					.when()
					.post("/booking")
					.then()
					.extract();
			return (response);
		}
		// response.jsonPath().getString("id");

		// System.out.println(response.getStatusCode()+"
		// "+response.jsonPath().getString("id")+" "+i);
}
