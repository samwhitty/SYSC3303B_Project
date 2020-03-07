package iteration3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {

	DatagramSocket receiveSocket, sendSocket;
	DatagramPacket receivePacket, sendPacket;
	byte[] data;
	ElevatorSubsystem[] elevators;
	int numElevators;
	
	List<Thread> threads =  new ArrayList<>();
	
	InetAddress schedulerAddress;
	
	//Used to create multiple elevators
	public ElevatorManager() {
		try {
			receiveSocket = new DatagramSocket(99);
			sendSocket = new DatagramSocket();
		} catch (SocketException se) {
			se.printStackTrace();
		}
		data = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);
	}
	
	//Number of elevators to be created sent by scheduler
	public int receiveData() {
		try {
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] received = receivePacket.getData();
		schedulerAddress = receivePacket.getAddress();
		
		return ByteBuffer.wrap(received).getInt();
		
	}
	
	public void initElevators(int num) {
		numElevators = num;
		elevators = new ElevatorSubsystem[numElevators];
		System.out.printf("Creating %d elevators", numElevators);
		
		for (int i = 0; i < numElevators; i++ ) {
			threads.add(new Thread(elevators[i]));
			threads.get(i).start();
		}
	}
	
	public void sendElevatorPositions() {
		int[] positions = new int[numElevators];
		byte[] data = null;
		for (int i = 0; i < numElevators; i++) {
			positions[i] = elevators[i].getCurrentFloor();
			data = ByteBuffer.allocate(4).putInt(positions[i]).array();
		}

		sendPacket = new DatagramPacket(data, data.length, schedulerAddress, 41);
		
		try {
			sendSocket.send(sendPacket);
			System.out.println("Elevator data sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		ElevatorManager manager = new ElevatorManager();
		
		while (true) {
			int received = manager.receiveData();
			
			//Scheduler send 99 to request elevators floors, otherwise used to init elevators
			if (received == 99) {
				manager.sendElevatorPositions();
			}
			else {
				manager.initElevators(received);
			}
		}
		
		
	}
	
}
