package apitesting;

import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import services.GetRequest;
import services.PostRequest;
import services.PutRequest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class UpdateBookingTest extends BaseTest{

    @Before
    public void setUp() {
        this.postRequest = new PostRequest();
        this.getRequest = new GetRequest();
        this.putRequest = new PutRequest();
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
    public void updateBookingTest() throws IOException, ParseException {
        myWriter.append("\n");
        myWriter.append(Helper.logHelper(Helper.LogType.START, "starting update booking test"));
        jsonListInit("data/LegalBookings.json");
        jsonObjectInit("data/UpdateLegalBooking.json");
        myWriter.append(Helper.logHelper(Helper.LogType.INFO, "parse bookings from json"));
        int i = 1;
        for (Object obj : bookings) {
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "testing booking #" + i));
            JSONObject booking = (JSONObject) obj;
            Response postResponse = postRequest.createBooking(booking);
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "create a booking with post request"));
            int responseCode = postResponse.getStatusCode();
            String bookingId = postResponse.jsonPath().getString("bookingid");
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post response returned: " + responseCode));
            if (responseCode != 200){
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "ERROR with POST request"));
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
                return;
            }

            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "post request finished successfully"));
            Response getResponse = getRequest.getSpecificBooking(bookingId);
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "looking for booking id: " + bookingId));
            if (getResponse.statusCode() != 200){
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Did not find booking id:" + bookingId));
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
                return;
            }
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "found booking with id: " + bookingId));
            String getBody = getResponse.getBody().jsonPath().getJsonObject("").toString();
            String postBody = postResponse.getBody().jsonPath().getJsonObject("booking").toString();
            if (getBody.equals(postBody))
                myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking #" + i + " created successfully"));
            else{
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Post response and get response are different"));
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
                return;
            }
            Response putResponse = putRequest.updateBooking(updatedBooking, bookingId);
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "update booking with id: " + bookingId));
            responseCode = postResponse.getStatusCode();
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "put response returned: " + responseCode));
            String desiredCheckoutDate = ((JSONObject)updatedBooking.get("bookingdates")).get("checkout").toString();
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "parse new checkout date from input file"));
            String newCheckoutDate = ((LinkedHashMap)putResponse.jsonPath().getJsonObject("bookingdates")).get("checkout").toString();
            myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "parse new checkout date from put response"));
            if (desiredCheckoutDate.equals(newCheckoutDate))
                myWriter.append(Helper.logHelper(Helper.LogType.DEBUG, "Booking updated successfully"));
            else{
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Input file and put response are different"));
                myWriter.append(Helper.logHelper(Helper.LogType.ERROR, "Test finished with errors"));
                return;
            }
            i++;
        }
        myWriter.append(Helper.logHelper(Helper.LogType.PASS, "Update booking Test finished successfully"));
    }

    public static void main(String[] args) {

        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        org.junit.runner.Result result = junit.run(UpdateBookingTest.class); // Replace "SampleTest" with the name of your class
        if (result.getFailureCount() > 0) {
            System.out.println("Test failed.");
            System.exit(1);
        } else {
            System.out.println("Test finished successfully.");
            System.exit(0);
        }
    }
}
