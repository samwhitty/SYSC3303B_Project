import java.io.*;
import java.nio.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


class FloorSubsystemTests {
	
    private TestHost host;

    // Variables for writing the test file
    private String filePath;
    private PrintWriter writer;

    // Number of requests 
    private int requestCount;
    
    // FloorSubsystem used for testing
    private FloorSubsystem testController;
    private int numFloors;
    private int numElevators;
    
    // The parsed requests from the sample test file
    private ArrayList<Integer[]> reqs;
    
   
     // Sets up the test environment for each test. Initializes the needed objects and writes a sample test file.
  
    @BeforeEach
    void setUp() throws Exception {
    	System.out.println("SETTING UP NEW TEST... ");
    	// Set the number of floors and elevators
    	numFloors = //anything;
    	numElevators = //anything;
    	
    	// Initialize the TestHost for receiving signalsS
        host = new TestHost(0, //scheduler port number, // Floor port number);

        host.disableResponse();
        
        // Create the sample test file
			//TODO
        
        // Read the requests from the text file
			//TODO
        
        // Parse the file
        testController = new FloorSubsystem(numFloors, numElevators);
        testController.parseInputFile(filePath);
        
        // Grab the arrayList of requests for use later
        reqs = testController.getRequests();
        
        System.out.println(" FINISHED SETUP" );
        System.out.println(" STARTING TEST ");
        
    }
	
	
	
	//TODO
}
    