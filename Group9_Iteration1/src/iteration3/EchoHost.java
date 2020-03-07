import java.io.*;
import java.net.*
import java.util.arrays;


public class EchoHost implements Runnable {    
  
	private int ReceivingPort; 
	private int DestinationPort;
	private InetAddress address; 

	// The DatagramSockets and DatagramPackets used for UDP
	private DatagramSocket sendSocket, receiveSocket;

	// Expected number of messages to receive
	private int expectedmessages;
	
	private boolean response;

	/**
	 * Constructor
	 * Creates a new EchoHost object.
	 
	 * @param expectedmessages  The number of messages to be received
	 * @param portNumber           The port number to receive packets from
	 * 
	 * @return None
	 */
	public EchoHost(int expectedmessages, 
        	        int receivePortNumber,
        	        int destPort) {
	    
		this.expectedmessages = expectedmessages;
		ReceivingPort = receivePortNumber;
		DestinationPort = destPort;
		
		try {
			sendSocket = new DatagramSocket();
			receiveSocket = new DatagramSocket(ReceivingPort);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}

		// Initialize the address to send information on
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void sendPacket(byte[] msg, InetAddress address, int portNum) {
		DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, address, portNum);

		System.out.println("Test: Sending packet:");
		System.out.println("Test: To address: " + sendPacket.getAddress());
		System.out.println("Test: Destination port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		System.out.print("Test: Containing (as bytes): ");
		System.out.println(Arrays.toString(sendPacket.getData()));
		
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	
	 */
	public DatagramPacket receivePacket(int expectedLen) {
		// Initialize the DatagramPacket used to receive requests
		byte data[] = new byte[expectedLen];
		receivePacket = new DatagramPacket(data, data.length);

		try {
			System.out.println("Test: Waiting...");
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			System.out.println("Test: Receive Socket Timed Out.\n" + e);
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Test: Packet received:");
		System.out.println("Test: From address: " + receivePacket.getAddress());
		System.out.println("Test: From port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		System.out.println("Length: " + len);
		String received = new String(data, 0, len);
		System.out.println("Containing: " + received);
		System.out.println(Arrays.toString(data) + "\n");
		
		return(receivePacket);
	}

	
	public int getPortNum() {
		return (ReceivingPort);
	}

	public void teardown() {
		sendSocket.close();
		receiveSocket.close();
	}
	
	public InetAddress getAddress() {
		return (address);
	}

	public void setExpectedNumMessages(int expectedNum) {
		expectedmessages = expectedNum;
	}
	
	public void disableResponse() {
	    response = false;
	}
	
   public void enableResponse() {
        response = true;
    }

	/**
	 * Run the EchoHost when called in a Thread object.
	 * Repeats the following for the number of expected messages:
	 *     Receive packet and send packet with the same contents back to the sender.
	
	 */
	@Override
	public void run() {
		int expectedLen = 5453453;//fake value for now
		
		DatagramPacket temp;

		// Receive and echo the expected number of messages
		for (int i = 0; i < expectedmessages; i++) {
			temp = receivePacket(expectedLen);
			if (response) {
			    sendPacket(temp.getData(), temp.getAddress(), DestinationPort);
			}
		}

	}
}
