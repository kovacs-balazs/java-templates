public void timer() {
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			System.out.println("Hello World!");
		}
	};
	timer.schedule(task, 0L, 1000L); // In millisecond (1 sec)
}

public static boolean isRunning = false;

public void delayedFunction() {
	if(isRunning) {
		return;
	}
	isRunning = true;
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			System.out.println("Hello World!");
			isRunning = false;
			cancel();
			/*
			Cancel the timer if you wanna make a delayed "function"
			You can create multiple delayed function, then just make a counter
			*/
		}
	};
	timer.schedule(task, 1000L, 1L);
}
