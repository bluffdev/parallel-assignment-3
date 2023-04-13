package pa3.two;

import java.util.concurrent.ConcurrentLinkedDeque;

public class Temp {
    public void run() {
        Thread[] threads = new Thread[8];
        ConcurrentLinkedDeque<Integer> list = new ConcurrentLinkedDeque<Integer>();

        for (int i = 0; i < 8; i++) {
            threads[i] = new Thread(new Sensor(list));
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
