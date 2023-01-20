package org.roxyfy.singleton;

public class DoubleCheckingLockingSingleton {
    private static DoubleCheckingLockingSingleton INSTANCE;

    private DoubleCheckingLockingSingleton() {}

    int x = 1;

    public static DoubleCheckingLockingSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleCheckingLockingSingleton.class) {
                if (INSTANCE == null)
                    INSTANCE = new DoubleCheckingLockingSingleton();// doesn't work ib case another thread will start
                //right after this line, but before end sync block, which guarantee that x = 1
                // This means no happens before relationship since another thread will see
                // that INSTANCE != null and don't need to go to sync block
            }
        }

        return INSTANCE;
    }
}
