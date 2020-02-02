/**
 * 
 */
package iteration1;
import java.io.*;
import java.math.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

import util.Request;


/**
 * @author Samuel Whitty & Said Omar & Everett Soldaat
 *
 */
public class ElevatorSubsystem {
	
	private BlockingQueue<Request> queue;
	public ElevatorSubsystem(BlockingQueue<Request> q) {
		this.queue = q;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Gonna get to this tomorrow.
	}
	
	public void openDoors(){
		//Ask TA for elevator door logic
	}
	
	public void closeDoors(){
	}
	
	
	public void sendRequest(Request request) throws InterruptedException {
		queue.put(request);
	}
	

}	
		
