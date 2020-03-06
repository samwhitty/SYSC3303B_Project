/**
 * 
 */
package iteration3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

import java.util.regex.MatchResult;

/**
 * @authors Everett Soldaat & Samuel Whitty & Michael Evans
 *
 */
public class FloorSubsystem implements Runnable {

	private static String direction;
	private byte[] data;

	

	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendReceiveSocket;
	
	File file = new File("src/iteration1/input.txt");
	Scanner s;

	private void initScanner() {
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * FloorSubsystem Constructor
	 * 
	 * @param send_q
	 * @param receive_q
	 */

	public FloorSubsystem() {
		try {
			sendReceiveSocket = new DatagramSocket(15);
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		initScanner();

	}

	/**
	 * Reads input from file. Should be passed a string with the full filename.
	 * Works for any plain text file. Must be in format of "HH/MM/SS.S"
	 */
	public synchronized boolean readInput() throws IOException {
		
		
		s.findInLine("(\\d+\\S+\\d+\\S+\\d+\\S+\\d) (\\d) ([a-zA-Z]+) (\\d)");
		MatchResult result = s.match();
		
		byte[] time = result.group(1).getBytes();
		
		byte[] floorNumB = {0, Byte.parseByte(result.group(2)), 0};

		direction = result.group(3);
		byte[] dByte = direction.getBytes();

		byte[] dFloorB = { 0, Byte.parseByte(result.group(4)), 0};

		data = new byte[time.length+ floorNumB.length + dByte.length + dFloorB.length];

		System.arraycopy(time, 0, data, 0, time.length);
		System.arraycopy(floorNumB, 0, data, time.length, floorNumB.length);
		System.arraycopy(dByte, 0, data, time.length+ floorNumB.length, dByte.length);
		System.arraycopy(dFloorB, 0, data, time.length +floorNumB.length + dByte.length, dFloorB.length);
		
		if (s.hasNext()) {
			s.nextLine();
			return true;

		}
		return false;
	}

	/**
	 * returns data
	 * 
	 * @return
	 */
	public byte[] getData() {
		return this.data;
	}

	/**
	 * Sends data from receive queue to out queue. Also empties the in queue.
	 */
	public synchronized void sendRequest() {

		System.out.println("Client: Sending Packet:");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		int len = sendPacket.getLength();
		System.out.println("Length: " + len);
		String s = new String(sendPacket.getData(), 0, len);
		System.out.println("Containing: " + s);
		System.out.println("Containing bytes: " + Arrays.toString(sendPacket.getData()));
		System.out.println();

		try {
			sendReceiveSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Takes data received from scheduler and prints it
	 */
	public synchronized void receiveRequest() {
		try {
			sendReceiveSocket.receive(receivePacket);
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
	
	public void rpc_send(byte[] out, byte[] in) {
		try {
			sendPacket = new DatagramPacket(out, out.length, InetAddress.getLocalHost(), 23);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.sendRequest();
		
		System.out.println("Client: Request sent.");
		System.out.println("Waiting for reply");
		System.out.println();
		receivePacket = new DatagramPacket(in, in.length);
		this.receiveRequest();
		
	}
	
	/**
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Floor Subsystem running.");
		System.out.println();
		boolean hasInput = true;
		while (hasInput) {
			try {
				hasInput = this.readInput();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] reply = new byte[100];
			this.rpc_send(data, reply);

		}

	}
	public static void main(String args[]) {
		FloorSubsystem h = new FloorSubsystem();
		Thread host = new Thread(h);
		host.start();
	}
}
