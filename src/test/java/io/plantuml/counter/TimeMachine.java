package io.plantuml.counter;

public class TimeMachine {

    private long timePosition = System.currentTimeMillis();

    public long now() {
        return timePosition;
    }

    public void moveForwardInFuture(long timeChange) {
        timePosition += timeChange;
    }
}
