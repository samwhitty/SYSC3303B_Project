/**
 * 
 */
package iteration3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;



import iteration2.SchedulerStateMachine.SchedulerState;

/**
 * @author Samuel Whitty & Michael Evans
 * 
 */
public class Scheduler extends Thread {

	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, e_receiveSocket, f_receiveSocket, m_receiveSocket;
	private static SchedulerState state;
	
	/**
	 * Constructor for the scheduler.
	 * @param einQueue
	 * @param eoutQueue
	 * @param finQueue
	 * @param foutQueue
	 * @param elev
	 * @param floor
	 */
	public Scheduler() {
		try {
			sendSocket = new DatagramSocket();
			f_receiveSocket = new DatagramSocket(23);
			e_receiveSocket = new DatagramSocket(41);
			m_receiveSocket = new DatagramSocket(56);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		this.state = SchedulerState.WAITFORREQUEST;
	}
	
	
	 //Close both send and receiver sockets
	
	protected void tearDown() {
		if (sendSocket != null) {
			sendSocket.close();
		}

		e_receiveSocket.close();
		f_receiveSocket.close();
		e_receiveSocket = null;
		f_receiveSocket = null;

	}
	 
	/**
	 * Getter for Scheduler State.
	 */
	public static SchedulerState getSchedulerState() {
		return state;
	}
	
	/**
	 * Sends a packet to ElevatorManager to create Elevators
	 * @param numElevators
	 */
	public void setup(byte numElevators) {
		byte[] data = ByteBuffer.allocate(4).putInt(numElevators).array();

		try {
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 99);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getElevatorPositions() {
		//adds 99 to data
		byte[] data = ByteBuffer.allocate(4).putInt(99).array();

		try {
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 99);
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method receives data from the floor subsystem. Also empties the out
	 * queue.
	 */
	public synchronized void receiveFloorData() {
		try {
			f_receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Client: Reply received:");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		System.out.println("Length: " + len);
		String received = new String(receivePacket.getData(), 0, len);
		System.out.println("Containing: " + received);
		System.out.println("Containing bytes: " + Arrays.toString(receivePacket.getData()));
		System.out.println();
	}

	/**
	 * This method sends data to the floor subsystem, and prints out the data being
	 * sent.
	 */
	public synchronized void sendDataToFloor(byte[] data) {
		try {
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 15);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Host: Sending Packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		String s = new String(sendPacket.getData(), 0, len);
		System.out.println("Containing: " + s);
		System.out.println("Containing bytes: " + Arrays.toString(sendPacket.getData()));
		System.out.println();
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * This method receives data from the elevator subsystem, and prints out the
	 * data received.
	 */
	public synchronized void receiveElevatorData() {
		try {
			e_receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Client: Reply received:");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		System.out.println("Length: " + len);
		String received = new String(receivePacket.getData(), 0, len);
		System.out.println("Containing: " + received);
		System.out.println("Containing bytes: " + Arrays.toString(receivePacket.getData()));
		System.out.println();

	}

	/**
	 * This method sends data to the elevator subsystem, and prints out the data
	 * sent.
	 */
	public synchronized void sendDataToElevator(byte[] data) {
		try {
			sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 69);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Host: Sending Packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		String s = new String(sendPacket.getData(), 0, len);
		System.out.println("Containing: " + s);
		System.out.println("Containing bytes: " + Arrays.toString(sendPacket.getData()));
		System.out.println();
		try {
			sendSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void receiveAndSend() {
		byte[] reply = new byte[100];
		byte[] data = new byte[100];
		byte[] request = new byte[100];
		
		receivePacket = new DatagramPacket(data, data.length);
		this.receiveFloorData();
		
		reply = ("Request Received").getBytes();
		this.sendDataToFloor(reply);
		
		receivePacket = new DatagramPacket(request, request.length);
		this.receiveElevatorData();
		
		this.sendDataToElevator(data);
		
		data = new byte[100];
		
		receivePacket = new DatagramPacket(data, data.length);
		this.receiveElevatorData();
		
		reply = ("Data Received").getBytes();
		this.sendDataToElevator(reply);
		
		request = new byte[100];
		receivePacket = new DatagramPacket(request, request.length);
		this.receiveFloorData();
		
		this.sendDataToFloor(data);
	}
	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Scheduler subsystem running.");

		while (true) {
			this.receiveAndSend();
			//if (!from_floor.isEmpty()) {
			//	receiveFloorData();
			//	state = state.next(data);
			//}

			//if (state == SchedulerState.DISPATCHELEVATOR) {
			//	sendDataToElevator();
			//	state = SchedulerState.WAITFORELEVATOR;
			//}

			//while (state == SchedulerState.WAITFORELEVATOR) {
			//	if (!from_elevator.isEmpty()) {
			//		this.receiveElevatorData();
			//		this.sendDataToFloor();
			//		state = SchedulerState.WAITFORREQUEST;
				//}
			//}
		}
	}

	/**
	 * Main Method.
	 */
	public static void main(String[] args) {

		Scheduler scheduler = new Scheduler();
		scheduler.setup((byte) 0x02);
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.getElevatorPositions();
		
		new Thread(scheduler).start();
	}

}
