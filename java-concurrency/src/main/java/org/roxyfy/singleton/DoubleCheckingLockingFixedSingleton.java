package org.roxyfy.singleton;

public class DoubleCheckingLockingFixedSingleton {
    private static volatile DoubleCheckingLockingFixedSingleton INSTANCE;

    private DoubleCheckingLockingFixedSingleton() {}

    int x = 1;

    public static DoubleCheckingLockingFixedSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleCheckingLockingFixedSingleton.class) {
                if (INSTANCE == null)
                    INSTANCE = new DoubleCheckingLockingFixedSingleton();
            }
        }

        return INSTANCE;
    }
}
