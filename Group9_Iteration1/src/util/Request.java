package util;

public class Request {
	private String time;
	private int floorNum;
	private String direction;
	private int destinationFloor;
	
	public Request(String time, int floorNum, String direction, int destinationFloor) {
		this.time = time;
		this.floorNum = floorNum;
		this.direction = direction;
		this.destinationFloor = destinationFloor;
	}
	public Object[] getRequest() {
		Object[] request = {time, floorNum, direction, destinationFloor};
		return request;
	}
	public void setRequest(String time, int floorNum, String direction, int destinationFloor) {
		this.time = time;
		this.floorNum = floorNum;
		this.direction = direction;
		this.destinationFloor = destinationFloor;
	}
}
