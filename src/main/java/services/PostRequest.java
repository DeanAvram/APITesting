package services;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class PostRequest {

	public static Response send_pst() {

		String requestBody = "{\n" + "  \"name\": \"formystudents\",\n" + "  \"job\": \"qaProffessional\"\n}";

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		Response response = (Response) given()
				.header("Content-type", "application/json")
				.and().body(requestBody)
				.when()
				.post("/api/users")
				.then()
				.extract();

		// String s_repronse=response.toString();

		// System.out.println(s_repronse);

		return (response);

		// response.jsonPath().getString("id");

		// System.out.println(response.getStatusCode()+"
		// "+response.jsonPath().getString("id")+" "+i);

	}

}
