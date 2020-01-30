/**
 * 
 */
package iteration1;

import util.ButtonDataStruct;
import util.TimeData;

/**
 * @author Samuel Whitty, 101002258
 * This code creates each of the three subsystems' threads.
 */
public class ElevatorSystem {

	/**
	 * The main method of the system.
	 * Starts the show.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String timeDataTest = "11;11;11.11";
		int[] carButtons = {1};
		
		
		TimeData time = new TimeData(timeDataTest);
		System.out.println(time.toString());
		
		ButtonDataStruct data = new ButtonDataStruct(true, false, time, timeDataTest, carButtons);
		System.out.println(data.toString());
	}

}
