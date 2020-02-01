/**
 * 
 */
package iteration1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * @author Samuel Whitty
 *
 */
public class FloorSubsystem {

	private static String time;
	private static int floorNum;
	private static String direction;
	private static int destinationFloor;

	public FloorSubsystem() {
		time = "00:00:00:00";
		floorNum = 0;
		direction = "Up";
		destinationFloor = 0;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
}
