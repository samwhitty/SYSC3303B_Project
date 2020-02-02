/**
 * 
 */
package iteration1;

import util.ButtonDataStruct;

/**
 * @author Samuel Whitty
 * 
 */
public class Scheduler extends Thread {
	private ButtonDataStruct data = null;
	private ElevatorSubsystem elevator = null;
	private FloorSubsystem floor = null;
	
	/**
	 * Constructor for the scheduler.
	 */
	public Scheduler(ButtonDataStruct data, ElevatorSubsystem elev, FloorSubsystem floor) {
		this.data = data;
		this.elevator = elev;
		this.floor  = floor;
		System.out.println("Need to implement \"Scheduler()\"");
	}
	
	/**
	 * This method reads data from the data object.
	 */
	public synchronized void readData() {
		System.out.println("Need to implement \"readData()\"");
	}
	
	/**
	 * This method sends data to the elevator subsystem.
	 */
	public synchronized void sendDataToElevator() {
		System.out.println("Need to implement \"sendDataToElevator()\".");
	}
	
	/**
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void sendDataToFloor() {
		System.out.println("Need to implement \"sendDataToFloor()\".");
	}
	
	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run(){

	}

}
