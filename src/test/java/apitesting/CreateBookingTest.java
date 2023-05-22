package apitesting;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import services.GetRequest;
import services.PostRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.io.FileWriter;
import java.io.IOException;


public class CreateBookingTest extends BaseTest{

	private GetRequest getRequest;
	private PostRequest postRequest;

	@Before
	public void setUp() {
		this.postRequest = new PostRequest();
		this.getRequest = new GetRequest();
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			myWriter = new FileWriter("postLog.txt");
			myWriter.write("String_Log\n");
		} catch (IOException e) {
			System.err.println("An error occurred.");
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		myWriter.close();
	}
	
	@Test
	public void testCerateBooking() throws IOException, ParseException {
		myWriter.append("\n");
		myWriter.append(Helper.logHelper(Helper.LogType.START, "starting create booking test"));
		jsonInit("data/orders.json");
		myWriter.append(Helper.logHelper(Helper.LogType.INFO, "parse bookings from json"));
		Response postResponse = null;
		int i = 1;
		for (Object obj : bookings) {
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "testing booking #" + i));
			JSONObject booking = (JSONObject) obj;
			postResponse = postRequest.createBooking(booking);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "create a booking with post request"));
			int responseCode = postResponse.getStatusCode();
			String bookingid = postResponse.jsonPath().getString("bookingid");
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post response returned: " + responseCode));
			if (responseCode != 200){
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "ERROR with POST request"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
				System.exit(1);
			}

			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post request finished successfully"));
			Response getResponse = getRequest.getSpecificBooking(bookingid);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "looking for booking id: " + bookingid));
			if (getResponse.statusCode() != 200){
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Did not find booking id:" + bookingid));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
				System.exit(1);
			}
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "found booking with id: " + bookingid));
			String getBody = getResponse.getBody().asString();
			String postBody = postResponse.getBody().jsonPath().getJsonObject("booking");
			if (getBody.equals(postBody)){

			}
			else{
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Post response and get response are different"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
				System.exit(1);
			}
			//System.out.println("GET: " + getResponse.getBody().asString());
			//System.out.println("POST BODY" + postResponse.getBody().jsonPath().getJsonObject("booking"));
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








