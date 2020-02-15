/**
 * 
 */
package iteration1;

import java.util.concurrent.BlockingQueue;

import iteration2.ElevatorStateMachine.State;
import util.TimeData;

/**
 * @author Samuel Whitty & Said Omar & Everett Soldaat & Michael Evans
 *
 */
public class ElevatorSubsystem implements Runnable {

	private BlockingQueue<Object[]> send_queue;
	private BlockingQueue<Object[]> receive_queue;
	private State state;
	private Object[] data;
	
	boolean stop = false;

	public ElevatorSubsystem(BlockingQueue<Object[]> in, BlockingQueue<Object[]> out) {
		this.receive_queue = in;
		this.send_queue = out;
		this.state = state.WAITING;
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
		state.next(data);
	}
	/*
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Elevator Subsystem running.");
		while(!stop) {
			if(this.dataRecieved()) {
				this.receiveRequest();
			}
		}
		
	}
}
