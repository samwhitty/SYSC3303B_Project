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
	
	/**
	 * Constructor for the scheduler.
	 * @param einQueue
	 * @param eoutQueue
	 * @param finQueue
	 * @param foutQueue
	 * @param elev
	 * @param floor
	 */
	public Scheduler(BlockingQueue<Object[]> einQueue, BlockingQueue<Object[]> eoutQueue,
			BlockingQueue<Object[]> finQueue, BlockingQueue<Object[]> foutQueue, ElevatorSubsystem elev,
			FloorSubsystem floor) {
		this.from_elevator = einQueue;
		this.to_elevator = eoutQueue;
		this.from_floor = finQueue;
		this.to_floor = foutQueue;
		this.elevator = elev;
		this.floor = floor;

		this.state = SchedulerState.WAITFORREQUEST;

	}

	/**
	 * Getter for Scheduler State.
	 */
	public static SchedulerState getSchedulerState() {
		return state;
	}

	/**
	 * This method receives data from the floor subsystem. Also empties the out
	 * queue.
	 */
	public synchronized void receiveFloorData() {
		try {
			System.out.println("Recieved Floor Data");
			System.out.println();
			data = from_floor.take();
			from_floor.clear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method sends data to the floor subsystem, and prints out the data being
	 * sent.
	 */
	public synchronized void sendDataToFloor() {
		System.out.println("Sending data to floor");
		to_floor.add(data);

	}

	/**
	 * This method receives data from the elevator subsystem, and prints out the
	 * data received.
	 */
	public synchronized void receiveElevatorData() {

		try {
			data = from_elevator.take();
			from_elevator.clear();

			System.out.println("Received data from Elevator_out_Queue:");
			System.out.println("Time: " + data[0]);
			System.out.println("Pasengers picked up from Floor: " + data[1]);
			System.out.println("Elevator Direction: " + data[2]);
			System.out.println("Elevator Destination " + data[3]);
			System.out.println("Elevator Current Floor " + data[4]);
			System.out.println();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method sends data to the elevator subsystem, and prints out the data
	 * sent.
	 */
	public synchronized void sendDataToElevator() {

		to_elevator.add(data);

		System.out.println("Sending data to Elevator_in_Queue:");
		System.out.println("Time: " + data[0]);
		System.out.println("Request Floor: " + data[1]);
		System.out.println("Request Direction: " + data[2]);
		System.out.println("Request Destination " + data[3]);
		System.out.println();
	}

	/**
	 * Runs the scheduler.
	 */
	@Override
	public void run() {
		System.out.println("Scheduler subsystem running.");

		while (true) {
			if (!from_floor.isEmpty()) {
				receiveFloorData();
				state = state.next(data);
			}

			if (state == SchedulerState.DISPATCHELEVATOR) {
				sendDataToElevator();
				state = SchedulerState.WAITFORELEVATOR;
			}

			while (state == SchedulerState.WAITFORELEVATOR) {
				if (!from_elevator.isEmpty()) {
					this.receiveElevatorData();
					this.sendDataToFloor();
					state = SchedulerState.WAITFORREQUEST;
				}
			}
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

		Scheduler scheduler = new Scheduler(from_elevator, to_elevator, from_floor, to_floor, elevator, floor);

		new Thread(floor).start();
		new Thread(elevator).start();
		new Thread(scheduler).start();
	}

}