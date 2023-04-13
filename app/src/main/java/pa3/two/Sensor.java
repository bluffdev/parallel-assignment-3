package pa3.two;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Sensor implements Runnable {
    private ConcurrentLinkedDeque<Integer> list;

    public Sensor(ConcurrentLinkedDeque<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        Random r = new Random();
        for (int i = 0; i < 60; i++) {
            list.add(r.nextInt(-100, 71));
        }
    }
}
