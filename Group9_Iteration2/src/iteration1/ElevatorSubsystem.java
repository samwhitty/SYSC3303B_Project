/**
 * 
 */
package iteration1;

import java.util.concurrent.BlockingQueue;

/**
 * @author Samuel Whitty & Said Omar & Everett Soldaat & Michael Evans
 *
 */
public class ElevatorSubsystem implements Runnable {

	private BlockingQueue<String> send_queue;
	private BlockingQueue<String> receive_queue;

	public ElevatorSubsystem(BlockingQueue<String> in, BlockingQueue<String> out) {
		this.receive_queue = in;
		this.send_queue = out;
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

	/*
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		System.out.println("Elevator Subsystem running.");
	}
}
