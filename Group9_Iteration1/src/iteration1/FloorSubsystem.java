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

/**
 * @author Samuel Whitty
 *
 */
public class FloorSubsystem implements Runnable {

	private static String time;
	private static int floorNum;
	private static String direction;
	private static int destinationFloor;
	
	private BlockingQueue<Request> queue;

	public FloorSubsystem(BlockingQueue<Request> q) {
		time = "00:00:00:00";
		floorNum = 0;
		direction = "Up";
		destinationFloor = 0;
		this.queue = q;

	}

	@Override
	public void run() {
		File file = new File("src/iteration1/input.txt");
		Scanner s;
		try {
			s = new Scanner(file);
			s.findInLine("(\\d+\\S\\d+\\S\\d+\\S\\d) (\\d) ([a-zA-Z]+) (\\d)");
			MatchResult result = s.match();
			time = result.group(1);
			floorNum = Integer.parseInt(result.group(2));
			direction = result.group(3);
			destinationFloor = Integer.parseInt(result.group(4));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Request request = new Request(time, floorNum, direction, destinationFloor);
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void readInput() throws IOException {
		File file = new File("src/iteration1/input.txt");
		Scanner s = new Scanner(file);
		s.findInLine("(\\d+\\S\\d+\\S\\d+\\S\\d) (\\d) ([a-zA-Z]+) (\\d)");
		MatchResult result = s.match();
		time = result.group(1);
		floorNum = Integer.parseInt(result.group(2));
		direction = result.group(3);
		destinationFloor = Integer.parseInt(result.group(4));
	}
	public void sendRequest(Request request) {
		
	}
}
