package pa3.one;

import java.util.concurrent.atomic.AtomicInteger;

public class BirthdayPresents {
    public void run() {
        Thread[] threads = new Thread[4];
        LockFreeList<Integer> list = new LockFreeList<>();
        AtomicInteger numOfCards = new AtomicInteger();

        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(new Servent(list, numOfCards));
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
