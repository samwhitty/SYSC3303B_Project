/**
 * 
 */
package util;

/**
 * @author Samuel Whitty
 * This data structure exists to hold button data
 * between the FloorSubsystem and ElevatorSubsystem.
 * 
 * Known Issues : 
 * 		The int[] object "carButtons" only works for arrays of size 1.
 */
public class ButtonDataStruct {
	private Boolean filled = false;
	private Boolean up = false;
	private Boolean down = false;
	private TimeData time;
	private String floor;
	private int[] carButtons;
	
	public Boolean isFull() {
		return filled;
	}
	
	public Boolean isUp() {
		return up;
	}
	
	public Boolean isDown() {
		return down;
	}
	
	public TimeData getTime() {
		return time;
	}
	
	public String getFloor() {
		return floor;
	}
	
	public int[] getCarButtons() {
		return carButtons;
	}
	
	public String toString() {
		return "Filled: " + filled.toString() + ", Up: " + up.toString() + ", Down: " + down.toString() + ", Time: " 
			+ time.toString() + ", Floors: " + floor.toString() + ", CarButtons: " + carButtons[0];
	}
	
	public ButtonDataStruct(Boolean up, Boolean down, TimeData time, String floor, int[] carButtons) {
		this.up = up;
		this.down = down;
		this.time = time;
		this.floor = floor;
		this.carButtons = carButtons;
		
		this.filled = true;
	}
}
