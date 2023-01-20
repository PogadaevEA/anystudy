package org.roxyfy.atomic.aboolean;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanExample {

    private final AtomicBoolean flag = new AtomicBoolean(false);

    public void doOnce(Runnable action) {
        if (flag.compareAndSet(false, true)) {
            action.run();
        }
    }
}
