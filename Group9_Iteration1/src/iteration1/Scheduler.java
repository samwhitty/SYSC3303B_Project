/**
 * 
 */
package iteration1;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import util.ButtonDataStruct;
import util.Request;

import iteration2.SchedulerStateMachine.SchedulerState;

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
	
	private static SchedulerState state;
	
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
		
		this.state = SchedulerState.WAITFORREQUEST;

	}
	
	public static SchedulerState getSchedulerState() {
		return state;
	}
	

	/*
	 * This method sends data to the elevator subsystem. Also empties the out queue.
	 */
	public synchronized void receiveFloorData() {
		try {
			floor.sendRequest();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void sendDataToElevator() {
		if(!foutQueue.isEmpty()) {
			foutQueue.drainTo(einQueue);
			System.out.println("Sending Floor out Queue to Elevator in queue");
		}
	}

	/*
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void receiveElevatorData() {
		try {
			elevator.sendRequest();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void sendDataToFloor() {
		if (!eoutQueue.isEmpty()) {
			eoutQueue.drainTo(finQueue);
			System.out.println("Sending Elevator out queue to floor in queue");
		}
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
		

		
		while (state == SchedulerState.DISPATCHELEVATOR) {
			sendDataToElevator();
			state = SchedulerState.WAITFORELEVATOR;
		}
		
		while (state == SchedulerState.WAITFORELEVATOR) {
			receiveElevatorData();
			if (!eoutQueue.isEmpty()) {
				state = SchedulerState.WAITFORREQUEST;
			}
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
