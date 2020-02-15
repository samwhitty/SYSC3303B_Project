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
		DISPATCHELEVATOR{
			@Override
			public SchedulerState next(Object[] data) {
				return DISPATCHELEVATOR;
			}
		},
		WAITFORELEVATOR{
			@Override
			public SchedulerState next(Object[] data) {
				return WAITFORELEVATOR;
			}
		},
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
