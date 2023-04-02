package io.plantuml.counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SCounter {
    private final static int SIZE = 128;
    public static final int MINIMUM_FOR_ACCURATE_VALUES = 1800;

    private final Lock lock = new ReentrantLock();

    private final Path dataFile = Paths.get("/tmp/counter/counter.txt");
    private long sumWhen;
    private long nb;
    private int peakPerMinute;

    public SCounter() {
        if (Files.isReadable(dataFile))
            try {
                final List<String> saved = Files.readAllLines(dataFile);
                this.nb = Long.parseLong(saved.get(0));
                this.sumWhen = Long.parseLong(saved.get(1));
                if (saved.size() > 2)
                    this.peakPerMinute = Integer.parseInt(saved.get(2));
            } catch (IOException e) {
                System.err.println("cannot read from " + dataFile);
            }
    }

    public void saveMeNow() {
        final List<String> data = new ArrayList<>();
        data.add("" + nb);
        data.add("" + sumWhen);
        data.add("" + peakPerMinute);
        try {
            // System.err.println("saving " + data + " to " + dataFile.toFile().getAbsolutePath());
            Files.write(dataFile, data);
            // System.err.println("saving ok");
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

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

    public int peakPerMinute() {
        return peakPerMinute;
    }


    public void computePeaks(long now) {
        final int current = diagramsPerMinute(now);
        lock.lock();
        try {
            if (current > peakPerMinute)
                peakPerMinute = current;
        } finally {
            lock.unlock();
        }
    }
}
