package io.plantuml.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SCounter {
    private final static int SIZE = 128;
    public static final int MINIMUM_FOR_ACCURATE_VALUES = 1800;

    private final Lock lock = new ReentrantLock();

    private long sumWhen;
    private long nb;

    public void increment(long now) {
        // See https://fossies.org/linux/haproxy/include/proto/freq_ctr.h for the theory

        lock.lock();
        try {
            if (nb == 0)
                sumWhen = now * (SIZE - 1);
            else
                sumWhen = sumWhen - (sumWhen + SIZE - 1) / SIZE + now;
            nb++;
        } finally {
            lock.unlock();
        }
    }

    public long getTotal() {
        lock.lock();
        try {
            return nb;
        } finally {
            lock.unlock();
        }
    }

    public long averageMillisecondsBetweenTwoTicks(long now) {
        lock.lock();
        try {
            if (nb < MINIMUM_FOR_ACCURATE_VALUES)
                return 0;
            final long mean = sumWhen / SIZE;
            final long diff = (now - mean);
            return diff / SIZE;
        } finally {
            lock.unlock();
        }
    }

    public int diagramsPerMinute(long now) {
        final long averageMillisecondsBetweenTwoTicks = averageMillisecondsBetweenTwoTicks(now);
        if (averageMillisecondsBetweenTwoTicks == 0)
            return 0;
        return (int) (60_000L / averageMillisecondsBetweenTwoTicks);
    }


}
