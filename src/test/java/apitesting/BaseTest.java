package apitesting;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.GetRequest;
import services.PostRequest;
import services.PutRequest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BaseTest {
    protected JSONArray bookings;

    protected JSONObject updatedBooking;
    static FileWriter myWriter;

    protected GetRequest getRequest;
    protected PostRequest postRequest;
    protected PutRequest putRequest;


    public void jsonListInit(String path) throws IOException, ParseException {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader;
            reader = new FileReader(path);
            //Read JSON file
            this.bookings = (JSONArray) jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void jsonObjectInit(String path) throws IOException, ParseException {
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader;
            reader = new FileReader(path);
            //Read JSON file
            this.updatedBooking = (JSONObject) jsonParser.parse(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}