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
	private BlockingQueue<String> einQueue = null;
	private BlockingQueue<String> eoutQueue = null;
	private BlockingQueue<String> finQueue = null;
	private BlockingQueue<String> foutQueue = null;

	/**
	 * Constructor for the scheduler.
	 */
	public Scheduler(BlockingQueue<String> einQueue, BlockingQueue<String> eoutQueue, BlockingQueue<String> foutQueue, BlockingQueue<String> finQueue,
			ElevatorSubsystem elev, FloorSubsystem floor) {
		this.einQueue = einQueue;
		this.eoutQueue = eoutQueue;
		this.finQueue = finQueue;
		this.foutQueue = foutQueue;
		this.elevator = elev;
		this.floor = floor;

	}

	/**
	 * This method sends data to the elevator subsystem.
	 * Also empties the out queue.
	 */
	public synchronized void sendDataToElevator() {
		System.out.println("Sending to elev");
		foutQueue.drainTo(einQueue);
	}

	/**
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void sendDataToFloor() {
		System.out.println("Sending to floor");
		eoutQueue.drainTo(finQueue);
	}

	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Adding arbitrary data to Elevator out queue");
		eoutQueue.add("Data");
		while (eoutQueue.isEmpty()) {
			System.out.println(foutQueue.isEmpty());
			if (!foutQueue.isEmpty()) {
				System.out.println("Ready to send from floor.");
				sendDataToElevator();
			}
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
		new Thread(scheduler).start();
		new Thread(elevator).start();
	}


}
