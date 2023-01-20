package org.roxyfy.singleton;

public class OnFirstRequestSingleton {
    private static OnFirstRequestSingleton INSTANCE;

    private OnFirstRequestSingleton() {}

    int x = 1;

    public static OnFirstRequestSingleton getInstance() {
        if (INSTANCE == null)
            INSTANCE = new OnFirstRequestSingleton();

        return INSTANCE;
    }

}
