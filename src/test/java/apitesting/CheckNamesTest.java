package apitesting;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import services.GetRequest;
import services.PostRequest;

import java.io.FileWriter;
import java.io.IOException;

public class CheckNamesTest extends BaseTest{

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
}
