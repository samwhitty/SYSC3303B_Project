/**
 * 
 */
package iteration1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import util.ButtonDataStruct;
import util.Request;

/**
 * @author Samuel Whitty & Michael Evans
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
	public Scheduler(BlockingQueue einQueue,BlockingQueue eoutQueue, BlockingQueue foutQueue, BlockingQueue finQueue, ElevatorSubsystem elev, FloorSubsystem floor) {
		this.einQueue = einQueue;
		this.eoutQueue= eoutQueue;
		this.finQueue = finQueue;
		this.foutQueue = foutQueue;
		this.elevator = elev;
		this.floor  = floor;
		
	}
	/**
	 * This method sends data to the elevator subsystem.
	 */
	public synchronized void sendDataToElevator() {
		foutQueue.drainTo(einQueue);
	}
	
	/**
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void sendDataToFloor() {
		eoutQueue.drainTo(finQueue);
	}
	
	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run(){
		for(int i =0; i <= 2; i++) {
			if(!eoutQueue.isEmpty()) {
				this.sendDataToFloor();
			}
			else if(!foutQueue.isEmpty()) {
				this.sendDataToElevator();
			}
		}
	}
	
	/**
	 * Main Method.
	 */
	public static void main(String[] args) {
		BlockingQueue<Request> floor_in_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<Request> floor_out_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<Request> elev_in_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<Request> elev_out_queue = new ArrayBlockingQueue<>(10);
		FloorSubsystem floor = new FloorSubsystem(floor_out_queue, floor_in_queue);
		ElevatorSubsystem elevator = new ElevatorSubsystem(elev_out_queue, elev_in_queue);
		Scheduler scheduler = new Scheduler(elev_in_queue, elev_out_queue, floor_in_queue, floor_out_queue, elevator, floor);
		
		new Thread(floor).start();
		new Thread(scheduler).start();
		new Thread(elevator).start();
	}

}
