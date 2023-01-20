package org.roxyfy.atomic.ainteger;

import java.util.concurrent.atomic.AtomicInteger;

public class GetAndIncrementCastLoop {

    public int getAndIncrement(AtomicInteger value, int sumValue) {
        int current;
        do {
            current = value.get();
        } while (!value.compareAndSet(current, current + sumValue));
        //false if no one do not update this at the same time

        return current;
    }

    // more optimal solution
    public int getAndIncrementOpti(AtomicInteger value, int sumValue) {
        return value.getAndUpdate(val -> val + sumValue);
    }
}
