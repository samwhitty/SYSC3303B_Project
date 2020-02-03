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
	private BlockingQueue<String> einQueue = null;
	private BlockingQueue<String> eoutQueue = null;
	private BlockingQueue<String> finQueue = null;
	private BlockingQueue<String> foutQueue = null;

	/*
	 * Constructor for the scheduler.
	 */
	public Scheduler(BlockingQueue<String> einQueue, BlockingQueue<String> eoutQueue, BlockingQueue<String> foutQueue,
			BlockingQueue<String> finQueue, ElevatorSubsystem elev, FloorSubsystem floor) {
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
	public synchronized Boolean dataWaiting() {
		if (eoutQueue.isEmpty() || foutQueue.isEmpty()) {
			System.out.println("No data to send.");
			return false;
		} else {
			System.out.println("Data ready to send");
			return true;
		}
	}

	/**
	 * Sends a filename to the FloorSubsystem for it to read.
	 */
	public synchronized void makeFloorRead(String input) {
		try {
			floor.readInput(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Scheduler subsystem running.");
		Boolean keepRunning = true;

		while (keepRunning) {
			makeFloorRead("input.txt");
			sendDataToElevator();
			sendDataToFloor();
		}
	}

	/**
	 * Main Method.
	 */
	public static void main(String[] args) {
		BlockingQueue<String> floor_in_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<String> floor_out_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<String> elev_in_queue = new ArrayBlockingQueue<>(10);
		BlockingQueue<String> elev_out_queue = new ArrayBlockingQueue<>(10);
		FloorSubsystem floor = new FloorSubsystem(floor_out_queue, floor_in_queue);
		ElevatorSubsystem elevator = new ElevatorSubsystem(elev_out_queue, elev_in_queue);
		Scheduler scheduler = new Scheduler(elev_in_queue, elev_out_queue, floor_in_queue, floor_out_queue, elevator,
				floor);

		new Thread(floor).start();
		new Thread(elevator).start();
		new Thread(scheduler).start();

	}

}
