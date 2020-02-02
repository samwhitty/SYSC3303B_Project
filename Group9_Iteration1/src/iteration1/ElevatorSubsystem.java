/**
 * 
 */
package iteration1;
import java.io.*;
import java.math.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;

import util.Request;


/**
 * @author Samuel Whitty & Said Omar & Everett Soldaat & Michael Evans
 *
 */
public class ElevatorSubsystem implements Runnable{
	
	private BlockingQueue<Request> einqueue;
	private BlockingQueue<Request> eoutqueue;
	public ElevatorSubsystem(BlockingQueue<Request> in, BlockingQueue<Request> out) {
		this.einqueue = in;
		this.eoutqueue = out;
	}
	
	public void sendRequest(Request request) throws InterruptedException {
		einqueue.put(request);
	}

	@Override
	public void run() {
		for(int i = 0; i <= 2; i++) {
			if(!einqueue.isEmpty()) {
				System.out.println(einqueue);
				einqueue.drainTo(eoutqueue);
			}
		}
	}
}	
		
