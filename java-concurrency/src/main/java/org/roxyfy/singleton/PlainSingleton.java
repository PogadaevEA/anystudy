package org.roxyfy.singleton;

public class PlainSingleton {
    private static final PlainSingleton INSTANCE = new PlainSingleton();

    private PlainSingleton() {}

    int x = 1;

    public static PlainSingleton getInstance() {
        return INSTANCE;
    }

}
