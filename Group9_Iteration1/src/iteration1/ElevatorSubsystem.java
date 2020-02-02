/**
 * 
 */
package iteration1;
import java.io.*;
import java.math.*;
import java.net.*;
/**
 * @author Samuel Whitty
 * @author Said Omar
 *
 */
public class ElevatorSubsystem {
	DatagramPacket sendPacket, receivePacket;
        DatagramSocket sendSocket, receiveSocket;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Gonna get to this tomorrow.
	}
	
	public void openDoors(){
		//Ask TA for elevator door logic
	}
	
	public void closeDoors(){
	}
	
	
	public String validatePacket(byte[] data){
		//checking if input data is valid since all byte values are less than 127
		for(int s = 1; s<data.length;s++){
			if (data[s] > 127 && data[s] < 0){
				return "invalid packet value";
			}
		}
	//add other stuff
	}
	
	
	public boolean validateRequest(){
		{
			//validates the text sent to the elevator is a valid command
		}
			
		
	public void exchangeData(){
		//Make a DatagramPacket for receiving oackets
		
		
		
		
		
		//Receive the data from the sxheduler to control the motor and the doors
		
		
		
		
		
		//try catch for waiting until a diagram packet is received from the socket
		
		
		
		
		
		
		
		// process the received datagram packet
		
		
		
		
		//make a string assuming we use an array for the values?
		
		
		
		

	}
}
		
		
