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

	private BlockingQueue<Object[]> send_queue;
	private BlockingQueue<Object[]> receive_queue;
	private EState state;
	private Object[] data;
	private int currentFloor;

	public ElevatorSubsystem(BlockingQueue<Object[]> in, BlockingQueue<Object[]> out) {
		this.receive_queue = in;
		this.send_queue = out;
		this.state = EState.WAITING;
		this.currentFloor = 1;
	}

	/*
	 * Sends request.
	 * Also empties the in queue.
	 */
	public synchronized void sendRequest() {
		send_queue.add(data);
		
	}
	
	public synchronized void receiveRequest() {
		try {
			data = receive_queue.take();
			data[4] = currentFloor;
			receive_queue.clear();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Elevator Subsystem running.");
		while(true) {
			if(!receive_queue.isEmpty()) {
				this.receiveRequest();
				while(state != EState.STOPPED) {
					state = state.next(data);
				}
				this.data = state.getData(data);
				currentFloor = (int) data[4];
				this.sendRequest();
			}
		}
		
	}
}
