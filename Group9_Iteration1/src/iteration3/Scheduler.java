/**
 * 
 */
package iteration3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import iteration3.SchedulerStateMachine.SchedulerState;

/**
 * @author Samuel Whitty & Michael Evans & Everett Soldaat
 * 
 */
public class Scheduler extends Thread {

	DatagramPacket sendPacket, f_receivePacket, e1_receivePacket, e2_receivePacket;
	DatagramSocket sendSocket, e1_receiveSocket,e2_receiveSocket, f_receiveSocket, m_receiveSocket;
	private static SchedulerState state;
	private boolean elevator1;
	private byte[] testData = new byte[100];
	
	
	/**
	 * Constructor for the scheduler.
	 */
	public Scheduler() {
		try {
			sendSocket = new DatagramSocket();
			f_receiveSocket = new DatagramSocket(23);
			e1_receiveSocket = new DatagramSocket(41);
			e2_receiveSocket = new DatagramSocket(42);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		this.state = SchedulerState.WAITFORREQUEST;
		elevator1 = true;
	}
	
	/**
	 * Close both send and receiver sockets
	 */
	public void tearDown() {
		if (sendSocket != null) {
			sendSocket.close();
		}
		e1_receiveSocket.close();
		e2_receiveSocket.close();
		f_receiveSocket.close();
	}
	/**
	 * Gets data for testing
	 * @return
	 */
	public byte[] getData() {
		return this.testData;
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
	
	public DatagramSocket selectElevator() {
		DatagramSocket socket;
		DatagramPacket otherPacket;
		byte[] data1 = new byte[100];
		byte[] data2 = new byte[100];
		e1_receivePacket = new DatagramPacket(data1, data1.length);
		e2_receivePacket = new DatagramPacket(data2, data2.length);
		
		try {
			e1_receiveSocket.receive(e1_receivePacket);
			e2_receiveSocket.receive(e2_receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte requestedFloor = f_receivePacket.getData()[12];
		byte e1Floor = e1_receivePacket.getData()[0];
		byte e2Floor = e2_receivePacket.getData()[0];
		
		if (Math.abs(requestedFloor - e1Floor) < Math.abs(requestedFloor - e2Floor)) {
			elevator1 = true;
			sendPacket = new DatagramPacket(f_receivePacket.getData(), f_receivePacket.getData().length, e1_receivePacket.getAddress(), 69);
			socket = e1_receiveSocket;
			System.out.println("Sending Request to Elevator 1");
			
			byte[] wait = ("Wait").getBytes();
			otherPacket = new DatagramPacket(wait, wait.length, e2_receivePacket.getAddress(), 70 );
		} else {
			elevator1 = false;
			sendPacket = new DatagramPacket(f_receivePacket.getData(), f_receivePacket.getData().length, e2_receivePacket.getAddress(),70);
			socket = e2_receiveSocket;
			System.out.println("Sending Request to Elevator 2");
			
			byte[] wait = ("Wait").getBytes();
			otherPacket = new DatagramPacket(wait, wait.length, e1_receivePacket.getAddress(), 69 );
		}
		System.out.println();	
		try {
			sendSocket.send(sendPacket);
			sendSocket.send(otherPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socket;	
 	}
	

	/**
	 * This method receives data from the floor subsystem. Also empties the out
	 * queue.
	 */
	public synchronized byte[] receiveFloorData() {
		byte[] data = new byte[100];
		f_receivePacket = new DatagramPacket(data, data.length);
		try {
			f_receiveSocket.receive(f_receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Scheduler: New request received from Floor:");
		System.out.println("From host: " + f_receivePacket.getAddress());
		System.out.println("Host port: " + f_receivePacket.getPort());
		int len = f_receivePacket.getLength();
		System.out.println("Length: " + len);
		String received = new String(f_receivePacket.getData(), 0, len);
		System.out.println("Containing: " + received);
		System.out.println("Containing bytes: " + Arrays.toString(f_receivePacket.getData()));
		System.out.println();
		testData = data;
		return f_receivePacket.getData();
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
		System.out.println("Scheduler: Sending Packet to Floor:");
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
	public synchronized byte[] receiveElevatorData(DatagramSocket socket) {
		byte[] data = new byte[100];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Scheduler: Reply received from Elevator:");
		System.out.println("From host: " + packet.getAddress());
		System.out.println("Host port: " + packet.getPort());
		int len = packet.getLength();
		System.out.println("Length: " + len);
		String received = new String(packet.getData(), 0, len);
		System.out.println("Containing: " + received);
		System.out.println("Containing bytes: " + Arrays.toString(packet.getData()));
		System.out.println();

		byte[] receive = ("Received").getBytes();
		DatagramPacket responsePacket;
		if (elevator1) {
			responsePacket = new DatagramPacket(receive, receive.length, packet.getAddress(), 69 );
		} else {
			responsePacket = new DatagramPacket(receive, receive.length, packet.getAddress(), 70 );
		}
		
		
		try {
			sendSocket.send(responsePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return packet.getData();
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
		System.out.println("Scheduler: Sending Packet to Elevator:");
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
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Scheduler subsystem running.");
		DatagramSocket return_socket = null;	
		
		while (true) {
				byte[] data = receiveFloorData();
				
				state = state.next(data);

			if (state == SchedulerState.DISPATCHELEVATOR) {
				return_socket = selectElevator();
				state = SchedulerState.WAITFORELEVATOR;
			}

			if (state == SchedulerState.WAITFORELEVATOR) {
				
				data = receiveElevatorData(return_socket);
				this.sendDataToFloor(data);
				state = SchedulerState.WAITFORREQUEST;	
			}
		}
	}

	/**
	 * Main Method.
	 */
	public static void main(String[] args) {

		Scheduler scheduler = new Scheduler();
		
		new Thread(scheduler).start();
	}
}
