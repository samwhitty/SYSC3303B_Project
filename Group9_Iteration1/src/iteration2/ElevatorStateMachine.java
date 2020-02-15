package iteration2;

import java.util.concurrent.BlockingQueue;

import iteration1.ElevatorSubsystem;

public class ElevatorStateMachine extends ElevatorSubsystem{

	public ElevatorStateMachine(BlockingQueue<Object[]> in, BlockingQueue<Object[]> out) {
		super(in, out);
	}

	public static enum State {
		
		WAITING {
			@Override
			public State next(Object[] data) {
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
			public State next(Object[] data) {
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
			public State next(Object[] data) {
				if()
			}
		},
		DOWN{
			@Override
			public State next(Object[] data) {
				return null;
			}
		},
		UNLOADING{
			@Override
			public State next(Object[] data) {
				return null;
			}
		},
		STOPPED {
			@Override
			public State next(Object[] data) {
				
				return null;
			}
		},;

		public State next(Object[] data) {
			// Leave unimplemented
			return null;
		}
	}

	public static void main(String[] args) {

	}
}
