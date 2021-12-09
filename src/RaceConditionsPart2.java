import java.util.concurrent.Semaphore;

public class RaceConditionsPart2 {
	public static Semaphore s;

	public static void main(String[] args) throws InterruptedException {

		// Semaphores:
		// https://learn.zybooks.com/zybook/CPPCS4310DongFall2021/chapter/4/section/2
		// Project Description:
		// https://learn.zybooks.com/zybook/CPPCS4310DongFall2021/chapter/11/section/3
		// use semaphores so race conditions don't happen
		// resource:
		// https://bowenli86.github.io/2016/09/17/java/concurrency/Java-Concurrency-wait-notify-and-notifyAll/

		s = new Semaphore(1);

		int n = 100;

		int[] buffer = new int[n];

		// producer thread
		Runnable producer = () -> {
			int next_in = 0;
			int data;
			while (true) {
				int k1 = random(5, 25); // "short bursts"
				for (int i = 0; i < k1; i++) {
					data = buffer[(next_in + i) % n];
					if (data == 1) { // consumer too slow
						synchronized (s) {
							try {
								s.wait(); // locked, wait for consumer
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					buffer[(next_in + i) % n] += 1;
				}
				next_in = (next_in + k1) % n;
				synchronized (s) {
					s.notify(); // notify consumer if consumer is locked to continue execution
				}
				int t1 = random(0, 5000); // 0 to 5 seconds
				try {
					Thread.sleep(t1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				System.out.print("producer: ");
				printBuffer(buffer);
			}
		};

		// consumer thread
		Runnable consumer = () -> {
			int next_out = 0;
			int data;
			while (true) {
				int t2 = random(0, 5000); // 0 to 5 seconds
				try {
					Thread.sleep(t2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				int k2 = random(5, 25); // "short bursts"
				for (int i = 0; i < k2; i++) {
					data = buffer[(next_out + i) % n];
					if (data == 0) { // producer too slow
						s.release(); // release so producer can starting producing
						synchronized (s) {
							try {
								s.wait(); // locked, wait for producer
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					buffer[(next_out + i) % n] -= 1;
				}
				next_out = (next_out + k2) % n;
				synchronized (s) {
					s.notify(); // notify producer if producer is locked to continue execution
				}
				System.out.print("consumer: ");
				printBuffer(buffer);
			}
		};

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);
		producerThread.start();
		consumerThread.start();
	}

	public static void print(Object o) {
		System.out.print(o.toString());
	}

	public static int random(int a, int b) { // random [a, b]
		return a + (int) (Math.random() * ((b - a) + 1));
	}

	public static void printBuffer(int[] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			System.out.print(buffer[i]);
		}
		System.out.println();
	}
}
