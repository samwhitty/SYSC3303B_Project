package util;

public class Request {
	private TimeData time;
	private int floorNum;
	private String direction;
	private int destinationFloor;
	
	public Request(TimeData time, int floorNum, String direction, int destinationFloor) {
		this.time = time;
		this.floorNum = floorNum;
		this.direction = direction;
		this.destinationFloor = destinationFloor;
	}
	public Object[] getRequest() {
		Object[] request = {time, floorNum, direction, destinationFloor};
		return request;
	}
	public void setRequest(TimeData time, int floorNum, String direction, int destinationFloor) {
		this.time = time;
		this.floorNum = floorNum;
		this.direction = direction;
		this.destinationFloor = destinationFloor;
	}
	public String toString() {
		return "Time: " + time + " Floornum "+ Integer.toString(floorNum) + " Direction " + direction + " Destinatin Floor " + Integer.toString(destinationFloor) ;
	}
}
