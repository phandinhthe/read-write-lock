package org.example.thread;

public interface ManualThread {
	void stop();

	 static void stop(ManualThread manualThread) {
		manualThread.stop();
	}
}
