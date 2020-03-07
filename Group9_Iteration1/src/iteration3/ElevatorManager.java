package iteration3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class ElevatorManager {

	DatagramSocket receiveSocket;
	DatagramPacket receivePacket;
	byte[] data;
	ElevatorSubsystem[] elevators;
	
	//Used to create multiple elevators
	public ElevatorManager() {
		try {
			receiveSocket = new DatagramSocket(99);
		} catch (SocketException se) {
			se.printStackTrace();
		}
		data = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);
	}
	
	//Number of elevators to be created sent by scheduler
	public void receiveData() {
		try {
			receiveSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] received = receivePacket.getData();
		int numElevators = ByteBuffer.wrap(received).getInt();
		
		System.out.printf("Creating %d elevators", numElevators);
		
		for (int i = 0; i < numElevators; i++ ) {
			elevators[i] = new ElevatorSubsystem();
		}
	}
	
	public static void main(String[] args) {
		
		ElevatorManager manager = new ElevatorManager();
		manager.receiveData();
		
		
	}
	
}
