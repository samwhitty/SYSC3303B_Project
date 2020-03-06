package iteration3;


import util.TimeData;

public class ElevatorStateMachine {
	private static double oneToTwo = 2.75;
	private static double average = 2.18;
	private static double loading = 10.2;
	private static double travelTime;
	private static byte[] newData = new byte[5];

	public static enum EState {
		/**
		 * Determines what state to go to
		 * from the data sent
		 */
		WAITING {
			@Override
			public EState next(byte[] data) {
				if ((int) data[1] < (int) data[4]) {
					return PICKDOWN;
				} else if ((int) data[1] > (int) data[4]) {
					return PICKUP;
				} else {
					return LOADING;
				}
			}
		},
		/**
		 * Simulates loading into the elevator
		 */
		LOADING {
			@Override
			public EState next(byte[] data) {
				travelTime += loading;
				if (data[2] == (String) "Up") {
					return UP;
				} else {
					return DOWN;
				}
			}
		},
		/**
		 * Simulates going up in the elevator
		 */
		UP {
			@Override
			public EState next(byte[] data) {
				int ftravel = (int) data[3] - (int) data[1];
				if (ftravel > 1) {
					travelTime += oneToTwo;
					for (int i = 1; i < ftravel; i++) {
						if (i + 1 == ftravel) {
							travelTime += oneToTwo;
						} else {
							travelTime += average;
						}
					}
					return UNLOADING;
				} else {
					travelTime += oneToTwo;
					return UNLOADING;
				}
			}
		},
		/**
		 * Simulates going down
		 */
		DOWN {
			@Override
			public EState next(byte[] data) {
				int ftravel = (int) data[1] - (int) data[3];
				if (ftravel > 1) {
					travelTime += oneToTwo;
					for (int i = 1; i < ftravel; i++) {
						if (i + 1 == ftravel) {
							travelTime += oneToTwo;
						} else {
							travelTime += average;
						}
					}
					return UNLOADING;
				} else {
					travelTime += oneToTwo;
					return UNLOADING;
				}

			}
		},
		/**
		 * Simulates the elevator going up 
		 * to pick up person
		 */
		PICKUP {
			@Override
			public EState next(byte[] data) {

				int ftravel = (int) data[1] - (int) data[4];
				if (ftravel > 1) {
					travelTime += oneToTwo;
					for (int i = 1; i < ftravel; i++) {
						if (i + 1 == ftravel) {
							travelTime += oneToTwo;
						} else {
							travelTime += average;
						}
					}
					data[4] = data[1];
					return LOADING;
				} else {
					travelTime += oneToTwo;
					return LOADING;
				}
			}
		},
		/**
		 * Simulates going down to pick up 
		 * Person
		 */
		PICKDOWN {
			@Override
			public EState next(byte[] data) {
				int ftravel = (int) data[4] - (int) data[1];
				if (ftravel > 1) {
					travelTime += oneToTwo;
					for (int i = 1; i < ftravel; i++) {
						if (i + 1 == ftravel) {
							travelTime += oneToTwo;
						} else {
							travelTime += average;
						}
					}
					data[4] = data[1];
					return LOADING;
				} else {
					travelTime += oneToTwo;
					return LOADING;
				}
			}
		},
		/**
		 * Simulates Unloading
		 */
		UNLOADING {
			@Override
			public EState next(byte[] data) {
				travelTime += loading;
				return STOPPED;
			}
		},
		/**
		 * Simulates elevator
		 * Stopped after drop off
		 */
		STOPPED {
			@Override
			public byte[] getData(byte[] data) {
				//newData = data;
				//TimeData time = (TimeData) newData[0];
				//time.setSec(travelTime);
				//String newtime = time.toString();
				//newData[0] = (TimeData) time;
				//newData[4] = (int) newData[3];
				//return newData;
			}
		},;

		public byte[] getData(byte[] data) {
			return null;
		}

		public EState next(byte[] data) {
			// Leave unimplemented
			return null;
		}
	}

}
