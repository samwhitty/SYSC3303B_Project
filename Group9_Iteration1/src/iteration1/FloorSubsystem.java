/**
 * 
 */
package iteration1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.regex.MatchResult;

import iteration2.SchedulerStateMachine.SchedulerState;
import util.TimeData;

/**
 * @authors Everett Soldaat & Samuel Whitty
 *
 */
public class FloorSubsystem implements Runnable {

	private static TimeData time;
	private static int floorNum;
	private static String direction;
	private static int destinationFloor;

	private BlockingQueue<Object[]> send_queue;
	private BlockingQueue<Object[]> receive_queue;

	private Object[] data =  new Object[4];

	File file = new File("src/iteration1/input.txt");
	Scanner s;

	private void initScanner() {
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public FloorSubsystem(BlockingQueue<Object[]> send_q, BlockingQueue<Object[]> receive_q) {
		time = new TimeData();
		floorNum = 0;
		direction = "Up";
		destinationFloor = 0;
		this.send_queue = send_q;
		this.receive_queue = receive_q;
		initScanner();

	}

	/*
	 * Reads input from file. Should be passed a string with the full filename.
	 * Works for any plain text file. Must be in format of "HH/MM/SS.S"
	 */
	public synchronized boolean readInput() throws IOException {
		s.findInLine("(\\d+\\S\\d+\\S\\d+\\S\\d) (\\d) ([a-zA-Z]+) (\\d)");
		MatchResult result = s.match();
		time = new TimeData(result.group(1));
		System.out.println("Line : " + time);
		floorNum = Integer.parseInt(result.group(2));
		direction = result.group(3);
		destinationFloor = Integer.parseInt(result.group(4));
		data[0] = time;
		data[1] = floorNum;
		data[2] = direction;
		data[3] = destinationFloor;
		if (s.hasNext()) {
			s.nextLine();
			return true;

		}
		return false;
	}

	/*
	 * Sends data from receive queue to out queue. Also empties the in queue.
	 */
	public synchronized void sendRequest() throws InterruptedException {
		System.out.println("Moving Floor in Queue data to out Queue");
		receive_queue.add(data);
		receive_queue.drainTo(send_queue);
		try {
			wait(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void receiveRequest() {
		send_queue.drainTo(receive_queue);
	}
	

	/*
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Floor Subsystem running.");

		while(true) {
			if(send_queue.isEmpty() && receive_queue.isEmpty()) {
				if(Scheduler.getSchedulerState() == SchedulerState.WAITFORREQUEST) {
					try {
						sendRequest();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
}
