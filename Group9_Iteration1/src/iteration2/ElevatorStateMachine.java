package iteration2;

public class ElevatorStateMachine {
	
	public static enum State{
		WAITING{
			@Override public State next(Action action){
				return null;
			}
		},
		MOVING{
			@Override public State next(Action action){
				return null;
			}
		},
		STOPPED{
			@Override public State next(Action action){
				
				return null;
			}
		},;

		public State next(Action action) {
			// Leave unimplemented
			return null;
		}
	}
	public static enum Action{
		UP,
		DOWN,
		WAIT,
		FINISH
	}
	public static void main(String[] args) {
        
    }
}
