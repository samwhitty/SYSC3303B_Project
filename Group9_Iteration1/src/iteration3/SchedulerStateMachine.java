package iteration3;


public class SchedulerStateMachine {


	public static enum SchedulerState{
		/**
		 * Waits for request
		 */
		WAITFORREQUEST{
			@Override
			public SchedulerState next(byte[] data) {
				int index = 0;
				while(data[index] != 0) {
					index++;
				}
				index += 3;
				byte[] d = {data[index], data[index+1]};
				
				if (data[2] == (String) "Up") {
					if ((int) data[1] > (int) data[3]) {
						return INVALID;
					} else if ((int) data[1] == (int) data[3]) {
						return INVALID;
					}
				}
				
				if (data[2] == (String) "Down") {
					if ((int) data[1] < (int) data[3]) {
						return INVALID;
					} else if ((int) data[1] == (int) data[3]) {
						return INVALID;
					} 
				}
				return DISPATCHELEVATOR;
			}
		},
		/**
		 * State for dispatching 
		 * data to elevator
		 */
		DISPATCHELEVATOR{
			@Override
			public SchedulerState next(byte[] data) {
				return DISPATCHELEVATOR;
			}
		},
		/**
		 * State for waiting for
		 * elevator responses
		 */
		WAITFORELEVATOR{
			@Override
			public SchedulerState next(byte[] data) {
				return WAITFORELEVATOR;
			}
		},
		/**
		 * State for invalid 
		 * Request
		 */
		INVALID{
			@Override
			public SchedulerState next(byte[] data) {
				return INVALID;
			}
		};
		
		public SchedulerState next(byte[] data) {
			return null;
		}
		
	}
	
}
