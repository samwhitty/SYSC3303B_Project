/**
 * 
 */
package iteration1;

import java.util.concurrent.BlockingQueue;

import util.ButtonDataStruct;
import util.Request;

/**
 * @author Samuel Whitty
 * 
 */
public class Scheduler extends Thread {
	private ButtonDataStruct data = null;
	private ElevatorSubsystem elevator = null;
	private FloorSubsystem floor = null;
	private BlockingQueue einQueue = null;
	private BlockingQueue eoutQueue = null;
	private BlockingQueue finQueue = null;
	private BlockingQueue foutQueue = null;
	
	/**
	 * Constructor for the scheduler.
	 */
	public Scheduler(BlockingQueue einQueue,BlockingQueue eoutQueue, BlockingQueue foutQueue, BlockingQueue finQueue, ButtonDataStruct data, ElevatorSubsystem elev, FloorSubsystem floor) {
		this.einQueue = einQueue;
		this.eoutQueue= eoutQueue;
		this.finQueue = finQueue;
		this.foutQueue = foutQueue;
		this.data = data;
		this.elevator = elev;
		this.floor  = floor;
		System.out.println("Need to implement \"Scheduler()\"");
	}
	
	/**
	 * This method sends data to the elevator subsystem.
	 */
	public synchronized void sendDataToElevator() {
		foutQueue.drainTo(einQueue);
		System.out.println("Need to implement \"sendDataToElevator()\".");
	}
	
	/**
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void sendDataToFloor() {
		eoutQueue.drainTo(finQueue);
		System.out.println("Need to implement \"sendDataToFloor()\".");
	}
	
	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run(){
		
	}
	
	/**
	 * Main Method.
	 */
	public static void main() {
		
	}

}
