/**
 * 
 */
package util;

/**
 * @author Samuel Whitty
 * 
 * Custom time object.
 */
public class TimeData {
	private int hour;
	private int minute;
	private int second;
	private int millisecond;
	private String timeString;
	
	/*
	 * Parses a String object in the form "hh:mm:ss.mm.mm"
	 * into a String[4] object.
	 */
	public String[] parseTime(String time) {
		String[] parsedTime = new String[5];
		
		parsedTime[0] = time.substring(0,1);
		parsedTime[1] = time.substring(3,4);
		parsedTime[2] = time.substring(6,7);
		parsedTime[3] = time.substring(9);
		
		return parsedTime;
	}
	
	public int hour() {
		return this.hour;
	}
	public int minute() {
		return this.minute;
	}
	
	public int second() {
		return this.second;
	}
	
	public int millisecond() {
		return this.millisecond;
	}
	
	public String toString() {
		return timeString;
	}
	
	public TimeData() {
		this.hour = 0;
		this.minute = 0;
		this.second = 0;
		this.millisecond = 0;
		this.timeString = "00;00;00.0";
	}
	
	public TimeData(String time) {
		String[] parsedTime = parseTime(time);
		
		this.hour = Integer.parseInt(parsedTime[0]);
		this.minute = Integer.parseInt(parsedTime[1]);
		this.second = Integer.parseInt(parsedTime[2]);
		this.millisecond = Integer.parseInt(parsedTime[3]);
		this.timeString = time;
		
		parsedTime = null;
	}
}
