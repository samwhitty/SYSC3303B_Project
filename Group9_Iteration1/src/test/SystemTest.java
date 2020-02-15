package test;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Before;
import org.junit.Test;

import iteration1.ElevatorSubsystem;
import iteration1.FloorSubsystem;
import iteration1.Scheduler;
import util.Request;

public class SystemTest {
	BlockingQueue<String> floor_in_queue;
	BlockingQueue<String> floor_out_queue;
	BlockingQueue<String> elev_in_queue;
	BlockingQueue<String> elev_out_queue;
	FloorSubsystem floor;
	ElevatorSubsystem elevator;
	Scheduler scheduler;
	
	
	@Before
	public void setUp() {
		floor_in_queue = new ArrayBlockingQueue<>(10);
		floor_out_queue = new ArrayBlockingQueue<>(10);
		elev_in_queue = new ArrayBlockingQueue<>(10);
		elev_out_queue = new ArrayBlockingQueue<>(10);
		//floor = new FloorSubsystem(floor_out_queue, floor_in_queue);
		//elevator = new ElevatorSubsystem(elev_out_queue, elev_in_queue);
		//scheduler = new Scheduler(elev_in_queue, elev_out_queue, floor_in_queue, floor_out_queue, elevator, floor);
	}
	@Test
	public void dataTransfer() throws IOException, InterruptedException {
//		floor.readInput("input.txt");
		floor.sendRequest();
//		assertTrue("checks is queue is empty");
		
	}
}
