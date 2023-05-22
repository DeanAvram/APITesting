package apitesting;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import services.GetRequest;
import services.PostRequest;

import java.io.IOException;


public class CreateBookingTest {
	
	private BaseTest baseTest;

	private GetRequest getRequest;
	private PostRequest postRequest;
	Logger logger = LogManager.getLogger(CreateBookingTest.class);

	@Before
	public void setUp() {
		this.baseTest = new BaseTest();
		this.postRequest = new PostRequest();
		this.getRequest = new GetRequest();
	}
	
	@Test
	public void testCerateBooking() throws IOException, ParseException {
		logger.info("starting create booking test");
		baseTest.jsonInit("data/orders.json");
		logger.debug("parse bookings from json");
		Response postResponse = null;
		int i = 1;
		for (Object obj : baseTest.bookings) {
			logger.debug("testing booking #" + i);
			JSONObject booking = (JSONObject) obj;
			postResponse = postRequest.createBooking(booking);
			logger.debug("create a booking with post request");
			int responseCode = postResponse.getStatusCode();
			String bookingid = postResponse.jsonPath().getString("bookingid");
			logger.debug("post response returned: " + responseCode);
			if (responseCode != 200){
				logger.error("ERROR with POST request");
				logger.error("Test finished with errors");
				System.exit(1);
			}
			logger.debug("post request finished successfully");
			Response getResponse = getRequest.getSpecificBooking(bookingid);
			logger.debug("looking for booking id: " + bookingid);
			if (getResponse.statusCode() != 200){
				logger.error("Did not find booking id: " + bookingid);
				logger.error("Test finished with errors");
				System.exit(1);
			}
			String xx = getResponse.jsonPath().getString("booking");
			String foundBookingId = getResponse.jsonPath().getString("bookingid");
			logger.debug("found booking id: " + foundBookingId);
			i++;
		}
		System.out.println(postResponse.body().asString());
		System.out.println(postResponse.statusCode());

	/*
		PostRequest postReq=new PostRequest();
		
		Response TestRes = postReq.send_pst();
		String s_repronse=TestRes.toString();
	    
		System.out.println(s_repronse);
		
		System.out.println(TestRes.getStatusCode()+" "+TestRes.jsonPath().getString("id")+" "+TestRes.jsonPath().getString("createdAt"));
		*/


	}
	
	
	public static void main(String args[]) {

		  JUnitCore junit = new JUnitCore();
		  junit.addListener(new TextListener(System.out));
		 org.junit.runner.Result result = junit.run(CreateBookingTest.class); // Replace "SampleTest" with the name of your class
		  if (result.getFailureCount() > 0) {
		    System.out.println("Test failed.");
		    System.exit(1);
		  } else {
		    System.out.println("Test finished successfully.");
		    System.exit(0);
		  }
		}


}








