package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import iteration3.ElevatorSubsystem;
import iteration3.FloorSubsystem;
import iteration3.Scheduler;
import iteration3.ElevatorStateMachine.EState;
import iteration3.SchedulerStateMachine.SchedulerState;
import util.Request;

public class SystemTest {

	FloorSubsystem floor;
	ElevatorSubsystem elevator;
	Scheduler scheduler;
	EState eState;
	SchedulerState sState;
	byte[] data;
	
	/**
	 * Set up 
	 */
	@Before
	public void setUp() {
		floor = new FloorSubsystem();
		elevator = new ElevatorSubsystem(41, 69);
		scheduler = new Scheduler();
		eState = EState.WAITING;
		sState = SchedulerState.WAITFORREQUEST;
	}
	/**
	 * Test data transfer between classes
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void dataTransfer() throws IOException, InterruptedException {
		floor.readInput();
		floor.sendRequest();
		assertTrue("checks if floor in queue isn't empty", !from_floor.isEmpty());
		scheduler.receiveFloorData();
		scheduler.sendDataToElevator(data);
		assertTrue("checks if elevator receive queue isn't empty", !to_elevator.isEmpty());
		elevator.receiveRequest();
		elevator.sendRequest();
		assertTrue("checks if elevator send queue isn't empty", !from_elevator.isEmpty());
	}
	/**
	 * Tests StateMachine States
	 * @throws IOException
	 */
	@Test
	public void stateMachineTest() throws IOException {
		floor.readInput();
		data = floor.getData();
		sState = sState.next(data);
		assertEquals("Checks if state has changed to DISPATCHELEVATOR", sState, SchedulerState.DISPATCHELEVATOR);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to PICKUP", eState, EState.PICKDOWN);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to LOADING", eState, EState.LOADING);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to DOWN", eState, EState.DOWN);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to UNLOADING", eState, EState.UNLOADING);
		eState = eState.next(data);
		assertEquals("Checks if state has changed to STOPPED", eState, EState.STOPPED);
		data = eState.getData(data);
		int index = data.length -1;
		while(data[index] ==0) {
			index--;
		}
		assertEquals("Checks is Elevator is on destination floor", data[index], data[index-2]);
	}
	
	
}
