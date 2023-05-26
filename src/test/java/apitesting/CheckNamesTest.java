package apitesting;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.TextListener;
import services.GetRequest;
import services.PostRequest;
import org.json.simple.parser.ParseException;
import org.junit.runner.JUnitCore;

import java.io.FileWriter;
import java.io.IOException;

public class CheckNamesTest extends BaseTest{

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
    
    @Test
	public void testCreateBookingwithoutNames() throws IOException, ParseException {
		myWriter.append("\n");
		myWriter.append(Helper.logHelper(Helper.LogType.START, "starting create booking with no first and last name"));
		jsonListInit("data/BookingsWithoutNames.json");
		myWriter.append(Helper.logHelper(Helper.LogType.INFO, "parse bookings from json"));
		int i = 1;
		for (Object obj : bookings) {
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "testing booking #" + i));
			JSONObject booking = (JSONObject) obj;
			Response postResponse = postRequest.createBooking(booking);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "create a booking with post request"));
			int responseCode = postResponse.getStatusCode();
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post response returned: " + responseCode));
			if (responseCode >= 200 && responseCode <= 299){
				//got a successful response instead of error
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Created a booking despite first and last name are null"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
				return;
			}
			else {
				myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking #" + i + " didn't created"));
			}
			i++;
		}
		myWriter.append(Helper.logHelper(Helper.LogType.PASS, "Create booking without first and last name Test finished successfully"));
	}


	@Test
	public void testCreateBookingEmptyName() throws IOException, ParseException {
		myWriter.append("\n");
		myWriter.append(Helper.logHelper(Helper.LogType.START, "starting create booking with empty first and last name"));
		jsonListInit("data/BookingsWithEmptyName.json");
		myWriter.append(Helper.logHelper(Helper.LogType.INFO, "parse bookings from json"));
		int i = 1;
		for (Object obj : bookings) {
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "testing booking #" + i));
			JSONObject booking = (JSONObject) obj;
			Response postResponse = postRequest.createBooking(booking);
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "create a booking with post request"));
			int responseCode = postResponse.getStatusCode();
			myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post response returned: " + responseCode));
			if (responseCode >= 200 && responseCode <= 299){
				//got a successful response instead of error
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Created a booking despite empty first name and last name"));
				myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Create booking with empty first and last name finished with errors"));
				return;
			}
			else {
				myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking #" + i + " didn't created"));
			}
			i++;
		}
		myWriter.append(Helper.logHelper(Helper.LogType.PASS, "Create booking with empty first and last name Test finished successfully"));
	}
	
	
	public static void main(String[] args) {

		  JUnitCore junit = new JUnitCore();
		  junit.addListener(new TextListener(System.out));
		 org.junit.runner.Result result = junit.run(CheckNamesTest.class); // Replace "SampleTest" with the name of your class
		  if (result.getFailureCount() > 0) {
		    System.out.println("Test failed.");
		    System.exit(1);
		  } else {
		    System.out.println("Test finished successfully.");
		    System.exit(0);
		  }
		}


}
