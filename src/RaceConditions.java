
public class RaceConditions {
	public static void main(String[] args) throws InterruptedException {

		// Semaphores:
		// https://learn.zybooks.com/zybook/CPPCS4310DongFall2021/chapter/4/section/2
		// Project Description:
		// https://learn.zybooks.com/zybook/CPPCS4310DongFall2021/chapter/11/section/3

		int n = 100;

		int[] buffer = new int[n];

		// producer thread
		Runnable producer = () -> {
			int next_in = 0;
			while (true) {
				int k1 = random(5, 25); // "short bursts"
				for (int i = 0; i < k1; i++) {
					buffer[(next_in + i) % n] += 1;
				}
				next_in = (next_in + k1) % n;
				int t1 = random(0, 5000); // 0 to 5 seconds
				try {
					Thread.sleep(t1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
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
					if (data != 1) {
						System.out.println("Race condition observed at index " + (next_out + i) % n);
						if (data == 0) { // producer too slow
							System.out.println("The producer was too slow, so the data had a value of 0");
						} else { // consumer too slow
							System.out.println("The consumer was too slow, so the data had a value greater than 1");
						}
						System.exit(0);
					}
					buffer[(next_out + i) % n] = 0;
				}
				next_out = (next_out + k2) % n;
				printBuffer(buffer);
			}
		};

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);
		producerThread.start();
		consumerThread.start();
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
