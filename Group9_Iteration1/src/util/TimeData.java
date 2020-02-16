/**
 * 
 */
package util;

/**
 * @author Samuel Whitty
 * 
 *         Custom time object.
 */
public class TimeData {
	private int hour;
	private int minute;
	private int second;
	private int millisecond;
	private String timeString;

	public void setHour(int hr) {
		this.hour = hr;
		updateTimeString();
	}
	

	public void setMin(int min) {
		this.minute = min % 60;
		this.hour += (int) Math.floor(min / 60);
		updateTimeString();
	}

	// Sets Seconds, but also checks if there is 60 or more seconds
	public void setSec(double sec) {
		double secon = Math.floor(sec);
		if (sec >= 60) {
			this.hour += sec % 360;
			this.minute += sec % 60;
			while (sec > 60) {
				sec -= 1000;
			}
			this.second = (int) sec;
			setMil((int) (sec - secon) * 1000);
		} else {
			this.millisecond += (int) (sec - secon) * 1000;
			this.second = (int) Math.floor(sec);
		}
		updateTimeString();
	}

	public void setMil(int mil) {
		if (mil > 1000) {
			this.hour += mil % 3600000;
			this.minute += mil % 60000;
			this.second += mil % 1000;
			while (mil > 1000) {
				mil -= 1000;
			}
			this.millisecond = mil;
		} else {
			this.millisecond = mil;
		}
		updateTimeString();
	}

	/*
	 * Parses a String object in the form "hh:mm:ss.mm.mm" into a String[4] object.
	 */
	public String[] parseTime(String time) {
		String[] parsedTime = new String[5];

		parsedTime[0] = time.substring(0, 1);
		parsedTime[1] = time.substring(3, 4);
		parsedTime[2] = time.substring(6, 7);
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
	
	public void updateTimeString() {
		this.timeString = Integer.toString(this.hour) + ";" + Integer.toString(this.minute)
			+ ";" + Integer.toString(this.second) + ";" + Integer.toString(this.millisecond);
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