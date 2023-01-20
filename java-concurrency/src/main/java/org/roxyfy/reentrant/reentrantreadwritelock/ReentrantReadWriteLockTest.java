package org.roxyfy.reentrant.reentrantreadwritelock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReentrantReadWriteLockTest {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    @Test
    void should_add_elements_to_list_when_multiple_threads() {
        ReentrantReadWriteLockExample reentrantReadWriteLockExample = new ReentrantReadWriteLockExample();
        Runnable r = () -> reentrantReadWriteLockExample.add("Hello");

        for (int repeats = 1; repeats <= 100; repeats++) {
            CompletableFuture<?> future = CompletableFuture.allOf(
                    Stream.generate(() -> CompletableFuture.runAsync(r))
                            .limit(THREADS)
                            .toArray(CompletableFuture[]::new)
            );
            future.join();

            assertEquals(repeats * THREADS, reentrantReadWriteLockExample.getList().size());
        }
    }
}
