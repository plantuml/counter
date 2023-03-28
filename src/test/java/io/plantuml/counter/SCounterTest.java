package io.plantuml.counter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SCounterTest {

    @Test
    public void basic() {
        final SCounter counter = new SCounter();
        Assertions.assertEquals(0, counter.getNb());
        counter.increment(1000L);
        Assertions.assertEquals(1, counter.getNb());

        // The rate is really slow
        Assertions.assertEquals(7, counter.getRate(2000));

        // The rate is really slow
        Assertions.assertEquals(15, counter.getRate(3000));
    }

    @Test
    public void everyMinute() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L * 60);
        }
        Assertions.assertEquals(60000L, counter.getRate(time.now()));
    }

    @Test
    public void everySecond() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
        }
        Assertions.assertEquals(1000L, counter.getRate(time.now()));
    }

    @Test
    public void everyOneAndTwoSecond() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 3000; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
            counter.increment(time.now());
            time.moveForwardInFuture(2000L);
        }
        // The rate the 1 every 1.5 s
        Assertions.assertEquals(1501L, counter.getRate(time.now()));
    }

    @Test
    public void changingRate() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 3000; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
            counter.increment(time.now());
            time.moveForwardInFuture(2000L);
        }
        // The rate the 1 every 1.5 s
        Assertions.assertEquals(1501L, counter.getRate(time.now()));

        // Now every second
        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
        }
        Assertions.assertEquals(1000L, counter.getRate(time.now()));

        // Now every minute
        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L * 60);
        }
        Assertions.assertEquals(60000L, counter.getRate(time.now()));


    }

}