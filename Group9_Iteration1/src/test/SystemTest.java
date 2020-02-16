package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;

import iteration1.ElevatorSubsystem;
import iteration1.FloorSubsystem;
import iteration1.Scheduler;
import iteration2.ElevatorStateMachine.EState;
import iteration2.SchedulerStateMachine.SchedulerState;
import util.Request;

public class SystemTest {
	BlockingQueue<Object[]> from_floor;
	BlockingQueue<Object[]> to_floor;
	BlockingQueue<Object[]> from_elevator;
	BlockingQueue<Object[]> to_elevator;

	FloorSubsystem floor;
	ElevatorSubsystem elevator;
	Scheduler scheduler;
	EState eState;
	SchedulerState sState;
	Object[] data;
	
	/**
	 * 
	 */
	@Before
	public void setUp() {
		from_floor = new ArrayBlockingQueue<>(10);
		to_floor = new ArrayBlockingQueue<>(10);
		from_elevator = new ArrayBlockingQueue<>(10);
		to_elevator = new ArrayBlockingQueue<>(10);
		floor = new FloorSubsystem(from_floor, to_floor);
		elevator = new ElevatorSubsystem(from_elevator, to_elevator);
		scheduler = new Scheduler(from_elevator, to_elevator, from_floor, to_floor, elevator, floor);
		eState = EState.WAITING;
		sState = SchedulerState.WAITFORREQUEST;
	}
	@Test
	public void dataTransfer() throws IOException, InterruptedException {
		floor.readInput();
		floor.sendRequest();
		assertTrue("checks if floor in queue isn't empty", !from_floor.isEmpty());
		scheduler.receiveFloorData();
		scheduler.sendDataToElevator();
		assertTrue("checks if elevator receive queue isn't empty", !to_elevator.isEmpty());
		elevator.receiveRequest();
		elevator.sendRequest();
		assertTrue("checks if elevator send queue isn't empty", !from_elevator.isEmpty());
	}
	@Test
	public void stateMachineTest() throws IOException {
		floor.readInput();
		data = floor.getData();
		sState = sState.next(data);
		assertEquals("Checks if state has changed to DISPATCHELEVATOR", sState, SchedulerState.DISPATCHELEVATOR);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to PICKUP", eState, EState.PICKUP);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to LOADING", eState, EState.LOADING);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to DOWN", eState, EState.DOWN);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to UNLOADING", eState, EState.UNLOADING);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to STOPPED", eState, EState.STOPPED);
		data = eState.getData(data);
		assertEquals("Checks is Elevator is on destination floor", (int) data[4], (int) data[3]);
	}
	
	
}
