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
	private Byte hour;
	private Byte minute;
	private Byte second;
	private Byte millisecond;
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
	
	public Byte hour() {
		return this.hour;
	}
	
	public Byte minute() {
		return this.minute;
	}
	
	public Byte second() {
		return this.second;
	}
	
	public Byte millisecond() {
		return this.millisecond;
	}
	
	public String toString() {
		return timeString;
	}
	
	public TimeData(String time) {
		String[] parsedTime = parseTime(time);
		
		this.hour = Byte.parseByte(parsedTime[0]);
		this.minute = Byte.parseByte(parsedTime[1]);
		this.second = Byte.parseByte(parsedTime[2]);
		this.millisecond = Byte.parseByte(parsedTime[3]);
		this.timeString = time;
		
		parsedTime = null;
	}
}
