package util;

import java.io.*;
import.java.net.*;
import org.junit.jupiter.api.*;

import iteration3.ElevatorSubsystem;

public class ElevatorSubsystemTest {
	 private EchoHost host;
	 private ElevatorSubsystem elevatorSubsystem;
	    
	    @BeforeEach
	    void setUp() throws Exception {
	        host = new TestHost(1,//scheduler port number, elevator port number);
	        
	        elevatorSubsystem = new ElevatorSubsystem();
	    }
		
		 @AfterEach
	    void tearDown() throws Exception {
	        host.teardown();
	        host = null;
	        
	        elevatorSubsystem.sendRequest = null;
	        elevatorSubsystem.receiveRequest = null;
	        elevatorSubsystem = null;
	    }
		
		
     // testSendData
     //Tests to see if the ElevatorSubsystem can successfully send packets
   
	@Test
	public void testSendData() throws UnknownHostException
	{
		//Setup the echo server to reply back and run it on its own thread
      Thread t = new Thread(host);
       t.start();
        
        //Send the data to the host
        elevatorSubsystem.sendData(new byte[] { values?, values?, values? },
       				   InetAddress.getLocalHost(),
        			   SchedulerPortNumber);
        
        // Check to see if the packet received is the same as the sent one
        //TODO
	}
	
	
    // testReceiveData 
    // Tests to see if the ElevatorSubsystem can successfully receive packets.
	
	@Test
	public void testReceiveData() throws IOException
	{	
        Thread t = new Thread(host);
        t.start();
        //Send a dummy message or System.exit is called
        host.sendPacket(new byte[] { value?, value?, value? }, InetAddress.getLocalHost(), //elevator port number);
        elevatorSubsystem.receiveRequest();
	}
}