package io.plantuml.counter;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class SCounterTest {

    @Test
    public void basic() {
        final SCounter counter = new SCounter();
        MatcherAssert.assertThat(counter.getTotal(), equalTo(0L));
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(2000), equalTo(0L)); // No rate Yet
        MatcherAssert.assertThat(counter.diagramsPerMinute(2000), equalTo(0));

        counter.increment(1000L);
        MatcherAssert.assertThat(counter.getTotal(), equalTo(1L));
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(2000), equalTo(0L)); // No rate Yet
        MatcherAssert.assertThat(counter.diagramsPerMinute(2000), equalTo(0));

        counter.increment(1000L);
        MatcherAssert.assertThat(counter.getTotal(), equalTo(2L));
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(2000), equalTo(0L)); // No rate Yet
        MatcherAssert.assertThat(counter.diagramsPerMinute(2000), equalTo(0));

    }

    @Test
    public void everyMinute() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L * 60);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(time.now()), equalTo(60_000L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(1));

    }

    @Test
    public void everySecond() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(time.now()), equalTo(1000L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(60));
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

        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(time.now()), equalTo(1501L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(39));
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
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(time.now()), equalTo(1501L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(39));

        // Now every second
        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(time.now()), equalTo(1000L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(60));

        // Now every minute
        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L * 60);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(time.now()), equalTo(60_000L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(1));


    }

    @Test
    public void startingOnePerSecond() {
        final TimeMachine time = new TimeMachine();
        final SCounter counter = new SCounter();
        for (int i = 0; i < 1799; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
            MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(0));
        }
        counter.increment(time.now());
        time.moveForwardInFuture(1000L);
        MatcherAssert.assertThat(counter.diagramsPerMinute(time.now()), equalTo(55));

    }


}