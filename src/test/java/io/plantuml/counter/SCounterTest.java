package io.plantuml.counter;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class SCounterTest {

    @Test
    public void basic() {
        final SCounter counter = new SCounter();
        MatcherAssert.assertThat(counter.getTotal(), equalTo(0L));
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(0L)); // No rate Yet
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(0));

        counter.increment(1000L);
        MatcherAssert.assertThat(counter.getTotal(), equalTo(1L));
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(0L)); // No rate Yet
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(0));

        counter.increment(1000L);
        MatcherAssert.assertThat(counter.getTotal(), equalTo(2L));
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(0L)); // No rate Yet
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(0));

    }

    @Test
    public void everyMinute() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L * 60);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(59_531L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(1));

    }

    @Test
    public void everySecond() {
        final SCounter counter = new SCounter();
        final TimeMachine time = new TimeMachine();

        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(992L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(60));
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

        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(1486L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(40));
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
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(1486L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(40));

        // Now every second
        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(992L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(60));

        // Now every minute
        for (int i = 0; i < 2400; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L * 60);
        }
        MatcherAssert.assertThat(counter.averageMillisecondsBetweenTwoTicks(), equalTo(59_531L));
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(1));


    }

    @Test
    public void startingOnePerSecond() {
        final TimeMachine time = new TimeMachine();
        final SCounter counter = new SCounter();
        for (int i = 0; i < 1799; i++) {
            counter.increment(time.now());
            time.moveForwardInFuture(1000L);
            MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(0));
        }
        counter.increment(time.now());
        time.moveForwardInFuture(1000L);
        MatcherAssert.assertThat(counter.diagramsPerMinute(), equalTo(56));

    }


}