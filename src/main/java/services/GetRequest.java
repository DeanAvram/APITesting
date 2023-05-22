package services;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GetRequest {

	public static Response send_res() {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response response = (Response) given()
				.header("Content-type", "application/json")
				.when()
				.get("/booking")
				.then()
				.extract();
		return (response);

	}

}
