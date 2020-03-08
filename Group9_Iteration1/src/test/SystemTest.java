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
	 * @throws IOException 
	 */
	@Before
	public void setUp() throws IOException {
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
		floor.sendRequest(floor.getData());
		scheduler.receiveFloorData();
		scheduler.sendDataToElevator(scheduler.getData());
		byte[] d = new byte[100];
		elevator.receiveRequest(d);
		floor.tearDown();
		elevator.tearDown();
		scheduler.tearDown();
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
		floor.tearDown();
		elevator.tearDown();
		scheduler.tearDown();
	}
	
	
}
