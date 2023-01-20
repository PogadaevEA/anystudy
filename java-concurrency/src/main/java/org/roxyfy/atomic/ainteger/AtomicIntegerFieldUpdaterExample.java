package org.roxyfy.atomic.ainteger;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterExample {

    private volatile int flag = 0;

    private final AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdaterExample> FLAG_UPDATER
            = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterExample.class, "flag");

    public void doOnce(Runnable action) {
        if (FLAG_UPDATER.compareAndSet(this, 0, 1)) {
            action.run();
        }
    }
}
