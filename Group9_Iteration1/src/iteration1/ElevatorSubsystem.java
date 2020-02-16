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
	private Object[] data = new Object[5];
	private int currentFloor;

	public ElevatorSubsystem(BlockingQueue<Object[]> in, BlockingQueue<Object[]> out) {
		this.receive_queue = in;
		this.send_queue = out;
		this.state = EState.WAITING;
		this.data[4] = 1;
		this.currentFloor = 1;
	}

	/*
	 * Sends request.
	 * Also empties the in queue.
	 */
	public synchronized void sendRequest() throws InterruptedException {
		System.out.println("Moving Elevator in Queue data to out Queue");
		receive_queue.drainTo(send_queue);
		receive_queue.clear();
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	/*
	 * Checks if it can send data.
	 */
	public synchronized Boolean canSend() {
		if (!send_queue.isEmpty()) {
			return true;
		}
		try {
			this.wait(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Can not print.");
		return false;
	}
	
	public synchronized boolean dataRecieved() {
		if(receive_queue.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	public synchronized void receiveRequest() {
		try {
			data = receive_queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!state.equals(EState.STOPPED)) {
			state = state.next(data);
		}
		
	}
	/*
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Elevator Subsystem running.");
		while(true) {
			if(this.dataRecieved()) {
				this.receiveRequest();
			}
		}
		
	}
}
