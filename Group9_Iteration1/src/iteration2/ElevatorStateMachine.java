package iteration2;

import java.util.concurrent.BlockingQueue;

import iteration1.ElevatorSubsystem;
import util.TimeData;

public class ElevatorStateMachine extends ElevatorSubsystem{

	public ElevatorStateMachine(BlockingQueue<Object[]> in, BlockingQueue<Object[]> out) {
		super(in, out);
	}

	public static enum EState {
		
		WAITING {
			@Override
			public EState next(Object[] data) {
				if((int) data[1] < (int) data[4]){
					return DOWN;
				}
				else if((int) data[1] > (int) data[4]) {
					return UP;
				}
				else {
					return LOADING;
				}
			}
		},
		LOADING{
			@Override
			public EState next(Object[] data) {
				TimeData time = (TimeData) data[0];
				if (data[2] == (String) "Up") {
					return UP;
				}
				else if (data[2] == (String) "Down") {
					return DOWN;
				}
				return null;
			}
		},
		UP {
			@Override
			public EState next(Object[] data) {
				TimeData time = (TimeData) data[0];
				int travel = (int) data[3] - (int) data[1]; 
				if(travel > 1) {
					
				}
			}
		},
		DOWN{
			@Override
			public EState next(Object[] data) {
				TimeData time = (TimeData) data[0];
				return null;
			}
		},
		UNLOADING{
			@Override
			public EState next(Object[] data) {
				return null;
			}
		},
		STOPPED {
			@Override
			public EState next(Object[] data) {
				
				return null;
			}
		},;

		public EState next(Object[] data) {
			// Leave unimplemented
			return null;
		}
	}

	public static void main(String[] args) {

	}
}
