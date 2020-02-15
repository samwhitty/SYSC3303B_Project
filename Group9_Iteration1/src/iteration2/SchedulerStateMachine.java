package iteration2;

import iteration1.Scheduler;

public class SchedulerStateMachine extends Scheduler {

	public SchedulerStateMachine() {
		super();
	}
	
	public static enum State{
		INIT{
			
		},
		WAITFORREQUEST{
			@Override
			public State next(Object[] data) {
				if (data[2] == (String) "Up") {
					if ((int) data[1] > (int) data[3]) {
						return INVALID;
					} else if ((int) data[1] == (int) data[3]) {
						return INVALID;
					} else {
						return DISPATCHELEVATOR;
					}
				}
			}
		},
		DISPATCHELEVATOR{
			@Override
			public State next(Object[] data) {
				return DISPATCHELEVATOR;
			}
		},
		WAITFORELEVATOR{
			@Override
			public State next(Object[] data) {
				return WAITFORELEVATOR;
			}
		},
		BLOCKED{
			
		},
		INVALID{
			@Override
			public State next(Object[] data) {
				return INVALID;
			}
		};
		
		public State next(Object[] data) {
			return null;
		}
		
	}
	
}
