package snippet;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Snippet {
	public static void main(String[] args) {
		Executor executor = Executors.newFixedThreadPool(10);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				System.out.println("task over");
				System.out.println(Thread.currentThread().toString());
			}
		};

		for(int i = 0; i < 14; i++) {
			executor.execute(task);
		}
		
		System.out.println("main---" + Thread.currentThread().toString());
		
		//下面可以替换Time
		executor = Executors.newScheduledThreadPool(10);
		ScheduledExecutorService scheduler = (ScheduledExecutorService) executor;
		scheduler.scheduleAtFixedRate(task, 10, 10, TimeUnit.SECONDS);
	}
}

