/**
 * 
 */
package iteration1;

import java.util.concurrent.BlockingQueue;

import iteration2.ElevatorStateMachine.EState;

import util.TimeData;

/**
 * @author Samuel Whitty & Said Omar & Everett Soldaat & Michael Evans
 *
 */
public class ElevatorSubsystem implements Runnable {

	private BlockingQueue<Object[]> to_scheduler;
	private BlockingQueue<Object[]> from_scheduler;
	private EState state;
	private Object[] data;
	private int currentFloor;

	/**
	 * ElevatorSubsystem Constructor
	 * @param out
	 * @param in
	 */
	public ElevatorSubsystem(BlockingQueue<Object[]> out, BlockingQueue<Object[]> in) {
		this.from_scheduler = in;
		this.to_scheduler = out;
		this.state = EState.WAITING;
		this.currentFloor = 1;
	}

	/**
	 * Sends request.
	 * Also empties the in queue.
	 */
	public synchronized void sendRequest() {
		System.out.println("Placing Data in out Queue");
		to_scheduler.add(data);
	}
	/**
	 * Receives request 
	 */
	public synchronized void receiveRequest() {
		try {
			System.out.println("Data Recieved");
			System.out.println("Taking Data from in Queue");
			System.out.println();
			data = from_scheduler.take();
			data[4] = currentFloor;
			from_scheduler.clear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Elevator Subsystem running.");
		while(true) {
			if(!from_scheduler.isEmpty()) {
				this.receiveRequest();
				while(state != EState.STOPPED) {
					state = state.next(data);
				}
				this.data = state.getData(data);
				currentFloor = (int) data[4];
				this.sendRequest();
				state = EState.WAITING;
			}
		}
	}
}

