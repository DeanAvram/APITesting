package apitesting;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BaseTest {
    protected JSONArray bookings;

    public void jsonInit(String path) throws IOException, ParseException {
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
}