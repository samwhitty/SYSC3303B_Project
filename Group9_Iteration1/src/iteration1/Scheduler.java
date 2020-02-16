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
	private Object[] data = null;
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
	public Scheduler(BlockingQueue<Object[]> einQueue, BlockingQueue<Object[]> eoutQueue, BlockingQueue<Object[]> finQueue,
			BlockingQueue<Object[]> foutQueue, ElevatorSubsystem elev, FloorSubsystem floor) {
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
			Object[] data_to_send = new Object[4];
			try {
				data_to_send = einQueue.take();
				
				System.out.println("Sending data to Elevator_in_Queue:");
				System.out.println("Time: " + data_to_send[0]);
				System.out.println("Request Floor: " + data_to_send[1]);
				System.out.println("Request Direction: " + data_to_send[2]);
				System.out.println("Request Destination" + data_to_send[3]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
		Object[] return_data = new Object[5];
		try {
			return_data = eoutQueue.take();
			
			System.out.println("Received data from Elevator_out_Queue:");
			System.out.println("Time: " + return_data[0]);
			System.out.println("Pasengers picked up from Floor: " + return_data[1]);
			System.out.println("Elevator Direction: " + return_data[2]);
			System.out.println("Elevator Destination" + return_data[3]);
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
		
		while(true) {
			if (!floor_out_queue())
			
			if (state == SchedulerState.DISPATCHELEVATOR) {
				sendDataToElevator();
				state = SchedulerState.WAITFORELEVATOR;
			}
			
			while (state == SchedulerState.WAITFORELEVATOR) {
				receiveElevatorData();
				if (!eoutQueue.isEmpty()) {
					state = SchedulerState.WAITFORREQUEST;
				}
			}
			state.next(data);
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
		FloorSubsystem floor = new FloorSubsystem(floor_in_queue, floor_out_queue);
		ElevatorSubsystem elevator = new ElevatorSubsystem(elev_in_queue, elev_out_queue);
		Scheduler scheduler = new Scheduler(elev_in_queue, elev_out_queue, floor_in_queue, floor_out_queue, elevator,
				floor);

		new Thread(floor).start();
		new Thread(elevator).start();
		new Thread(scheduler).start();

	}

}
