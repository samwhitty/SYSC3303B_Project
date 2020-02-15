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
				if (data[2] == (String) "Up") {
					if ((int) data[1] < (int) data[3]) {
						return STOPPED;
					} else if ((int) data[1] == (int) data[3]) {
						return STOPPED;
					} else {
						return LOADING;
					}
				} else if (data[2] == (String) "Down") {
					if ((int) data[1] > (int) data[3]) {
						return STOPPED;
					} else if ((int) data[1] == (int) data[3]) {
						return STOPPED;
					} else {
						return LOADING;
					}
				}
				
				return null;
			}
		},
		LOADING{
			
			
		},
		MOVING {
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
