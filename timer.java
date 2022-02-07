public static boolean isRunning = false;

public void timer() {
	if(isRunning) {
		return;
	}
	isRunning = true;
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
			public void run() {
			isRunning = false;
		// cancel(); // Cancel the timer if you wanna make an delayed function
		}
	};
	timer.schedule(task, 1000L, 1L);
}
