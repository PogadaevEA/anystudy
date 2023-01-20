package org.roxyfy.singleton;

public class LazyThreadSafeSingleton {
    private static LazyThreadSafeSingleton INSTANCE;

    private LazyThreadSafeSingleton() {}

    int x = 1;

    public static synchronized LazyThreadSafeSingleton getInstance() {//bad performance since sync on the method
        if (INSTANCE == null)
            INSTANCE = new LazyThreadSafeSingleton();

        return INSTANCE;
    }
}
