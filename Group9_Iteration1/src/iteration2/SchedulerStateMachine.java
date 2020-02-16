package iteration2;

import java.util.concurrent.BlockingQueue;

import iteration1.ElevatorSubsystem;
import iteration1.FloorSubsystem;
import iteration1.Scheduler;

public class SchedulerStateMachine extends Scheduler {

	
	
	public SchedulerStateMachine(BlockingQueue<Object[]> einQueue, BlockingQueue<Object[]> eoutQueue,
			BlockingQueue<Object[]> foutQueue, BlockingQueue<Object[]> finQueue, ElevatorSubsystem elev,
			FloorSubsystem floor) {
		super(einQueue, eoutQueue, foutQueue, finQueue, elev, floor);
		
	}

	public static enum SchedulerState{
		/**
		 * Waits for request
		 */
		WAITFORREQUEST{
			@Override
			public SchedulerState next(Object[] data) {
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
			public SchedulerState next(Object[] data) {
				return DISPATCHELEVATOR;
			}
		},
		/**
		 * State for waiting for
		 * elevator responses
		 */
		WAITFORELEVATOR{
			@Override
			public SchedulerState next(Object[] data) {
				return WAITFORELEVATOR;
			}
		},
		/**
		 * State for invalid 
		 * Request
		 */
		INVALID{
			@Override
			public SchedulerState next(Object[] data) {
				return INVALID;
			}
		};
		
		public SchedulerState next(Object[] data) {
			return null;
		}
		
	}
	
}
