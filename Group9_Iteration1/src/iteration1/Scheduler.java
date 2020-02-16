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
	private BlockingQueue<Object[]> from_elevator = null;
	private BlockingQueue<Object[]> to_elevator = null;
	private BlockingQueue<Object[]> from_floor = null;
	private BlockingQueue<Object[]> to_floor = null;
	
	private static SchedulerState state;
	
	
	/*
	 * Constructor for the scheduler.
	 */
	public Scheduler(BlockingQueue<Object[]> einQueue, BlockingQueue<Object[]> eoutQueue, BlockingQueue<Object[]> finQueue,
			BlockingQueue<Object[]> foutQueue, ElevatorSubsystem elev, FloorSubsystem floor) {
		this.from_elevator = einQueue;
		this.to_elevator = eoutQueue;
		this.from_floor = finQueue;
		this.to_floor = foutQueue;
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
			data = from_floor.take();
			from_floor.clear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void sendDataToElevator() {

			to_elevator.add(data);
			
			System.out.println("Sending data to Elevator_in_Queue:");
			System.out.println("Time: " + data[0]);
			System.out.println("Request Floor: " + data[1]);
			System.out.println("Request Direction: " + data[2]);
			System.out.println("Request Destination" + data[3]);	
	}

	/*
	 * This method sends data to the floor subsystem.
	 */
	public synchronized void receiveElevatorData() {

		Object[] return_data = new Object[5];
		try {
			return_data = from_elevator.take();
			from_elevator.clear();
			
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
		if (!to_elevator.isEmpty()) {
			to_elevator.drainTo(from_floor);
			System.out.println("Sending Elevator out queue to floor in queue");
		}
	}

	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Scheduler subsystem running.");
		
		while(true) {
			if (!from_floor.isEmpty()) {
				receiveFloorData();
			}
			
			if (state == SchedulerState.DISPATCHELEVATOR) {
				sendDataToElevator();
				state = SchedulerState.WAITFORELEVATOR;
			}
			
			while (state == SchedulerState.WAITFORELEVATOR) {
				receiveElevatorData();
				if (!to_elevator.isEmpty()) {
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
		BlockingQueue<Object[]> from_floor = new ArrayBlockingQueue<>(10);
		BlockingQueue<Object[]> to_floor = new ArrayBlockingQueue<>(10);
		BlockingQueue<Object[]> from_elevator = new ArrayBlockingQueue<>(10);
		BlockingQueue<Object[]> to_elevator = new ArrayBlockingQueue<>(10);
		
		FloorSubsystem floor = new FloorSubsystem(from_floor, to_floor);
		ElevatorSubsystem elevator = new ElevatorSubsystem(from_elevator, to_elevator);
		
		Scheduler scheduler = new Scheduler(from_elevator, to_elevator, from_floor, to_floor, elevator,
				floor);

		new Thread(floor).start();
		new Thread(elevator).start();
		new Thread(scheduler).start();

	}

}
