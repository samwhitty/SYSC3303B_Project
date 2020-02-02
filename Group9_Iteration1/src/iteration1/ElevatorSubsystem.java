/**
 * 
 */
package iteration1;

import java.util.concurrent.BlockingQueue;

import util.Request;

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
	private synchronized void sendRequest() throws InterruptedException {
		receive_queue.drainTo(send_queue);
		System.out.println("Subsystem sent data");
		receive_queue.clear();
	}

	/*
	 * Checks if it can send data.
	 */
	private synchronized Boolean canSend() {
		if (!send_queue.isEmpty()) {
			return true;
		}
		try {
			this.wait(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Sends data if possible, stops once it successfully sends data.
	 */
	@Override
	public void run() {
		Boolean done = false;
		while (!done) {
			System.out.println("Trying to send from Elevator");
			receive_queue.drainTo(send_queue);
			if (receive_queue.isEmpty()) {
				done = true;
			}
		}
	}
}
