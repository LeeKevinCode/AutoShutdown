package main;

public class MyThread extends Thread {
	
	protected volatile boolean running = true;
	
	public void terminate() {
        running = false;
    }
			
}
