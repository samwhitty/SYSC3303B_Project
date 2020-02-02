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

import util.Request;
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

	private BlockingQueue<String> send_queue;
	private BlockingQueue<String> receive_queue;

	public FloorSubsystem(BlockingQueue<String> send_q, BlockingQueue<String> receive_q) {
		time = new TimeData();
		floorNum = 0;
		direction = "Up";
		destinationFloor = 0;
		this.send_queue = send_q;
		this.receive_queue = receive_q;

	}

	@Override
	public void run() {
		Boolean done = false;
		while (!done) {
			try {
				sendRequest();
				done = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized static void readInput() throws IOException {
		File file = new File("src/iteration1/input.txt");
		Scanner s = new Scanner(file);
		s.findInLine("(\\d+\\S\\d+\\S\\d+\\S\\d) (\\d) ([a-zA-Z]+) (\\d)");
		MatchResult result = s.match();
		time = new TimeData(result.group(1));
		floorNum = Integer.parseInt(result.group(2));
		direction = result.group(3);
		destinationFloor = Integer.parseInt(result.group(4));
		s.close();
	}

	public synchronized void sendRequest() throws InterruptedException {
		System.out.println("Trying to send from FloorSubsystem");
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
}
