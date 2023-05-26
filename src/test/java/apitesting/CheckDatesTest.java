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


public class CheckDatesTest extends BaseTest{

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
			myWriter = new FileWriter("Log.txt");
			myWriter.write("Custom Log\n");
		} catch (IOException e) {
			System.err.println("An error occurred.");
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		myWriter.close();
	}

	//@Test
	public void testCreateBookingWithFullAndLegalBody() throws IOException, ParseException {
		myWriter.append("\n");
		myWriter.append(Helper.logHelper(Helper.LogType.START, "starting create booking with full body test"));
		jsonInit("data/LegalBookings.json");
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
			}

			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post request finished successfully"));
			Response getResponse = getRequest.getSpecificBooking(bookingid);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "looking for booking id: " + bookingid));
			if (getResponse.statusCode() != 200){
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Did not find booking id:" + bookingid));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
			}
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "found booking with id: " + bookingid));
			String getBody = getResponse.getBody().asString();
			String postBody = postResponse.getBody().jsonPath().getJsonObject("booking").toString();
			if (getBody.equals(postBody))
				myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking #" + i + " created successfully"));

			else{
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Post response and get response are different"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
				return;
			}
			i++;
		}
		myWriter.append(Helper.logHelper(Helper.LogType.PASS, "Create booking with full and legal booking Test finished successfully"));
	}

	@Test
	public void testCreateBookingWithoutBookingDates() throws IOException, ParseException {
		myWriter.append("\n");
		myWriter.append(Helper.logHelper(Helper.LogType.START, "starting create booking without booking dates test"));
		jsonInit("data/BookingsWithoutDates.json");
		myWriter.append(Helper.logHelper(Helper.LogType.INFO, "parse bookings from json"));
		Response postResponse = null;
		int i = 1;
		for (Object obj : bookings) {
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "testing booking #" + i));
			JSONObject booking = (JSONObject) obj;
			postResponse = postRequest.createBooking(booking);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "create a booking with post request"));
			int responseCode = postResponse.getStatusCode();
			if (responseCode >= 200 && responseCode <= 299){
				//got a successful response instead of error
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Created a booking despite that there are no dates"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
				return;
			}
			else {
				myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking #" + i + " didn't created"));
			}
			i++;
		}
		myWriter.append(Helper.logHelper(Helper.LogType.PASS, "Create booking without booking dates Test finished successfully"));
	}


	@Test
	public void testCreateBookingWithOldBookingDates() throws IOException, ParseException {
		myWriter.append("\n");
		myWriter.append(Helper.logHelper(Helper.LogType.START, "starting create booking with old booking dates test"));
		jsonInit("data/BookingsWithOldDates.json");
		myWriter.append(Helper.logHelper(Helper.LogType.INFO, "parse bookings from json"));
		Response postResponse = null;
		int i = 1;
		for (Object obj : bookings) {
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "testing booking #" + i));
			JSONObject booking = (JSONObject) obj;
			postResponse = postRequest.createBooking(booking);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "create a booking with post request"));
			int responseCode = postResponse.getStatusCode();
			if (responseCode >= 200 && responseCode <= 299){
				//got a successful response instead of error
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Created a booking despite that the dates are old"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Create booking with old booking dates finished with errors"));
				return;
			}
			else {
				myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking #" + i + " didn't created"));
			}
			i++;
		}
		myWriter.append(Helper.logHelper(Helper.LogType.PASS, "Create booking with old booking dates Test finished successfully"));
	}
	
	
	public static void main(String args[]) {

		  JUnitCore junit = new JUnitCore();
		  junit.addListener(new TextListener(System.out));
		 org.junit.runner.Result result = junit.run(CheckDatesTest.class); // Replace "SampleTest" with the name of your class
		  if (result.getFailureCount() > 0) {
		    System.out.println("Test failed.");
		    System.exit(1);
		  } else {
		    System.out.println("Test finished successfully.");
		    System.exit(0);
		  }
		}


}








