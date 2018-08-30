package bomberman;

/**
 * the timer is a thread which waits and calls the timeElapsed-method in a loop
 * the timer is defined to be a daemon-thread to ensure that it cant stop the
 * application from exiting
 * 
 * @author jonas
 * @date 05.05.17
 * 
 */
public class Timer extends Thread {

	private volatile boolean running = true;
	private int time;
	TimerListener listener;

	public Timer(int time_ms) {
		time = time_ms;
		this.setDaemon(true);
	}

	public Timer(int time_ms, TimerListener listener) {
		time = time_ms;
		this.listener = listener;
		this.setDaemon(true);
	}

	public void addTimerListener(TimerListener listener) {
		this.listener = listener;
	}

	@Override
	public void run() {
		while (running) {
			listener.timeElapsed();
			try {
				Thread.sleep(time);
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	public void kill() {
		running = false;
	}

}
