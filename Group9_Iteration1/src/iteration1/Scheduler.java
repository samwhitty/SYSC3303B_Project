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
	private BlockingQueue eQueue = null;
	private BlockingQueue fQueue = null;
	
	/**
	 * Constructor for the scheduler.
	 */
	public Scheduler(BlockingQueue eQueue, BlockingQueue fQueue, ButtonDataStruct data, ElevatorSubsystem elev, FloorSubsystem floor) {
		this.eQueue = eQueue;
		this.fQueue = fQueue;
		this.data = data;
		this.elevator = elev;
		this.floor  = floor;
		System.out.println("Need to implement \"Scheduler()\"");
	}
	
	/**
	 * This method sends data to the elevator subsystem.
	 */
	public synchronized void sendDataToElevator() {
		fQueue.drainTo(eQueue);
		System.out.println("Need to implement \"sendDataToElevator()\".");
	}
	
	/**
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void sendDataToFloor() {
		eQueue.drainTo(fQueue);
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
