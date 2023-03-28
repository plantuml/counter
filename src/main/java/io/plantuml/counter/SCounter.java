package io.plantuml.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SCounter {
    private final static int n = 128;

    private final Lock lock = new ReentrantLock();

    private long sumWhen;
    private long nb;

    public void increment(long now) {
        // See https://fossies.org/linux/haproxy/include/proto/freq_ctr.h for the theory

        lock.lock();
        try {
            if (nb == 0)
                sumWhen = now * (n - 1);
            else
                sumWhen = sumWhen - (sumWhen + n - 1) / n + now;
            nb++;
        } finally {
            lock.unlock();
        }
    }

    public long getNb() {
        lock.lock();
        try {
            return nb;
        } finally {
            lock.unlock();
        }
    }

    public long getRate(long now) {
        lock.lock();
        try {
            final long mean = sumWhen / n;
            final long diff = (now - mean);
            return diff / n;
        } finally {
            lock.unlock();
        }
    }


}
