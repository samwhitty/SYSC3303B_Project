import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import java.net.*;

class SchedulerTest {

	private Scheduler scheduler;
	private TestHost elevatorHost;
	private TestHost floorHost;

	/**
	 * Initialize scheduler
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		scheduler = new Scheduler();
		
		elevatorHost = new TestHost(0, 
                //elevator port number, 
                //scheduler port number);
		
		floorHost = new TestHost(0, 
                //floor port number, 
               // scheduler port number;
		
		elevatorHost.disableResponse();
		floorHost.disableResponse();
	}

	/**
	 * Make sure the scheduler and hosts are closed/set to NULL.
	 * 
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception {	        
		scheduler.tearDown();
		elevatorHost.teardown();
		floorHost.teardown();
		
		scheduler = null;
		elevatorHost = null;
		floorHost = null;
	}
	
	
	// Test to make sure the open elevator doors send/recieve works.
	
	@SuppressWarnings("static-access")
	@Test
	void testOpenElevatorDoors() throws UnknownHostException, InterruptedException {
		elevatorHost.setExpectedNumMessages(1);
		
		Thread thread = new Thread(elevatorHost);
		thread.start();
		//byte[] array = new array {values and values and values};
		// DatagramPacket packet = new DatagramPacket(array, array.length, InetAddress.getLocalHost(),elevatorportnumber);
		
		//open and close doors function?
	}
	
	
		