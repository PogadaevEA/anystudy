package org.roxyfy.singleton;

public class InitializationOnDemandHolderIdiomSingleton {
    int x = 1;

    static InitializationOnDemandHolderIdiomSingleton getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        static final InitializationOnDemandHolderIdiomSingleton INSTANCE = new InitializationOnDemandHolderIdiomSingleton();
    }
}
