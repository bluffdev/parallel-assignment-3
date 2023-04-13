package pa3.one;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Servent implements Runnable {
    private LockFreeList<Integer> list;
    private AtomicInteger numOfCards;
    private int total = 500000;

    public Servent(LockFreeList<Integer> list, AtomicInteger numOfCards) {
        this.list = list;
        this.numOfCards = numOfCards;
    }

    @Override
    public void run() {
        Random r = new Random();
        while (this.numOfCards.get() < total) {
            if (r.nextInt(10) < 3) {
                // Minotaur asks to check if an item is in the list
                this.list.contains(r.nextInt(total));
            }

            int newPresent = r.nextInt(total);
            this.list.add(newPresent);
            this.numOfCards.incrementAndGet();
            this.list.remove(newPresent);
        }
    }
}
