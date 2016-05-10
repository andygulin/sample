package examples.showcase.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	private ExecutorService pool = null;

	private ThreadPool() {
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
	}

	private static class Holder {
		private static final ThreadPool INSTANCE = new ThreadPool();
	}

	public static ThreadPool getInstance() {
		return Holder.INSTANCE;
	}

	public void execute(Runnable runnable) {
		pool.execute(runnable);
	}
}