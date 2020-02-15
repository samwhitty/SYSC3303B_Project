package iteration2;

public class ElevatorStateMachine {
	
	public static enum State{
		WAITING{
			@Override public State next(Object[] action){
				
				return null;
			}
		},
		MOVING{
			@Override public State next(Object[] action){
				return null;
			}
		},
		STOPPED{
			@Override public State next(Object[] action){
				
				return null;
			}
		},;

		public State next(Object[] action) {
			// Leave unimplemented
			return null;
		}
	}
	
	public static void main(String[] args) {
        
    }
}
