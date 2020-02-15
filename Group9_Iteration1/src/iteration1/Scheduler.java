/**
 * 
 */
package iteration1;

import java.io.IOException;
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
	private BlockingQueue<Object[]> einQueue = null;
	private BlockingQueue<Object[]> eoutQueue = null;
	private BlockingQueue<Object[]> finQueue = null;
	private BlockingQueue<Object[]> foutQueue = null;

	/*
	 * Constructor for the scheduler.
	 */
	public Scheduler(BlockingQueue<Object[]> einQueue, BlockingQueue<Object[]> eoutQueue, BlockingQueue<Object[]> foutQueue,
			BlockingQueue<Object[]> finQueue, ElevatorSubsystem elev, FloorSubsystem floor) {
		this.einQueue = einQueue;
		this.eoutQueue = eoutQueue;
		this.finQueue = finQueue;
		this.foutQueue = foutQueue;
		this.elevator = elev;
		this.floor = floor;

	}

	/*
	 * This method sends data to the elevator subsystem. Also empties the out queue.
	 */
	public synchronized void sendDataToElevator() {
		try {
			floor.sendRequest();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sending Floor out queue to floor in queue");
		foutQueue.drainTo(einQueue);
	}

	/*
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void sendDataToFloor() {
		try {
			elevator.sendRequest();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sending Elevator out queue to floor in queue");
		eoutQueue.drainTo(finQueue);
	}

	/**
	 * Checks if either of the queues has data to send.
	 */
	public synchronized Boolean dataWaitingFloor() {
		if (foutQueue.isEmpty()) {
			System.out.println("No data to send.");
			return false;
		} else {
			System.out.println("Data ready to send");
			return true;
		}
	}
	
	public synchronized Boolean dataWaitingeElevator() {
		if (eoutQueue.isEmpty()) {
			System.out.println("No data to send.");
			return false;
		} else {
			System.out.println("Data ready to send");
			return true;
		}
	}

	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Scheduler subsystem running.");
		try {
			while (floor.readInput()) {
				if(this.dataWaitingFloor()) {
					sendDataToFloor();
				}
				else if(this.dataWaitingeElevator()) {
					sendDataToElevator();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Main Method.
	 */
	public static void main(String[] args) {
		BlockingQueue<Object[]> floor_in_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<Object[]> floor_out_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<Object[]> elev_in_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<Object[]> elev_out_queue = new ArrayBlockingQueue<>(10);
		FloorSubsystem floor = new FloorSubsystem(floor_out_queue, floor_in_queue);
		ElevatorSubsystem elevator = new ElevatorSubsystem(elev_out_queue, elev_in_queue);
		Scheduler scheduler = new Scheduler(elev_in_queue, elev_out_queue, floor_in_queue, floor_out_queue, elevator,
				floor);

		new Thread(floor).start();
		new Thread(elevator).start();
		new Thread(scheduler).start();

	}

}
