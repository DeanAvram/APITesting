package services;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetRequest {

	public Response getSpecificBooking(String id) {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response response = (Response) given()
				.header("Content-type", "application/json")
				.when()
				.get("/booking/" + id)
				.then()
				.extract();
		return (response);

	}

}
