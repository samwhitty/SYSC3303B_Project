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


import iteration2.ElevatorStateMachine.EState;

/**
 * @author Samuel Whitty & Said Omar & Everett Soldaat & Michael Evans
 *
 */
public class ElevatorSubsystem implements Runnable {

	private EState state;
	private byte currentFloor;
	
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendSocket, receiveSocket; 

	/**
	 * ElevatorSubsystem Constructor
	 * @param out
	 * @param in
	 */
	public ElevatorSubsystem() {

		this.state = EState.WAITING;
		this.currentFloor = 1;
		try {
			receiveSocket = new DatagramSocket(69);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * Sends request.
	 * Also empties the in queue.
	 */
	public synchronized void sendRequest() {
		System.out.println("Server: Sending packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		String s = new String(sendPacket.getData(), 0, len);
		System.out.println("Containing: " + s);
		System.out.println("Containing bytes: " + Arrays.toString(sendPacket.getData()));
		System.out.println();
		try{ 
			sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
			sendSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	/**
	 * Receives request 
	 */
	public synchronized void receiveRequest(byte[] data) {
		receivePacket = new DatagramPacket(data, data.length);
		try {
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server: Packet received:");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		int len = receivePacket.getLength();
		System.out.println("Length: " + len);
		String received = new String(data, 0, len);
		System.out.println("Containing: " + received);
		System.out.println("Containing bytes: " + Arrays.toString(receivePacket.getData()));
		System.out.println();
		
	}
	/**
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Elevator Subsystem running.");
		while(true) {
			byte data[] = new byte[100];
			byte r[] = {currentFloor};
			
			try {
				sendPacket = new DatagramPacket(r, r.length, InetAddress.getLocalHost(), 41);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sendRequest();
			
			this.receiveRequest(data);
			int index = data.length -1;
			while(data[index] ==0) {
				index--;
			}
			index += 2;
			data[index] = currentFloor;
			
			//while(state != EState.STOPPED) {
			//	state = state.next(data);
			//}
			//data = state.getData(data);
			//currentFloor = (int) data[4];
			try {
				sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 41);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sendRequest();
			//state = EState.WAITING;
			r = new byte[100];
			this.receiveRequest(r);
		}
	}
	
	public static void main(String args[]) {
		
		ElevatorSubsystem e = new ElevatorSubsystem();
		Thread el = new Thread(e);
		el.start();
		
	}
}
