package apitesting;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import junit.framework.Assert;

import services.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateOrderTest {
	
	
	
	@Test
	public void parse_responce_and_print() throws SQLException {
		
	
		PostRequest postReq=new PostRequest();
		
		Response TestRes = postReq.send_pst();
		String s_repronse=TestRes.toString();
	    
		System.out.println(s_repronse);
		
		System.out.println(TestRes.getStatusCode()+" "+TestRes.jsonPath().getString("id")+" "+TestRes.jsonPath().getString("createdAt"));
		    
		   
		    
		       

	}
	
	
	public static void main(String args[]) {
		  JUnitCore junit = new JUnitCore();
		  junit.addListener(new TextListener(System.out));
		  org.junit.runner.Result result = junit.run(Send_PostResBin_and_print.class); // Replace "SampleTest" with the name of your class
		  if (result.getFailureCount() > 0) {
		    System.out.println("Test failed.");
		    System.exit(1);
		  } else {
		    System.out.println("Test finished successfully.");
		    System.exit(0);
		  }
		}
	

}








