package org.roxyfy.wrong_sync_shared_resources;

import java.util.ArrayList;
import java.util.List;

public class JavaConcurrencyApplication {

    public static void main(String[] args) throws InterruptedException {
        Runnable foo = () -> {
            Container container = new Container();
            for (int i = 0; i < 100_000; i++) {
                container.addEntry("foo");
            }
        };

        List<Thread> threads = new ArrayList<>();

        for (int count = 10; count > 0; count--) {
            Thread thread = new Thread(foo);
            thread.start();// start thread
            threads.add(thread);
        }

        for (Thread thread: threads) {
            thread.join();//wait the end of thread execution
        }

        System.out.println(Container.list.size());
    }
}