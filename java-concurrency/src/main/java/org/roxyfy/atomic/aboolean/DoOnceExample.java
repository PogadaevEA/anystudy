package org.roxyfy.atomic.aboolean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoOnceExample {

    final static int THREADS = Runtime.getRuntime().availableProcessors();

    @Test
    void should_run_only_once() throws InterruptedException {
        AtomicBooleanExample atomicBooleanExample = new AtomicBooleanExample();
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger count = new AtomicInteger(0);

        Runnable r = () -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            atomicBooleanExample.doOnce(count::incrementAndGet);
        };

        List<Thread> threads = Stream.generate(() -> new Thread(r))
                .limit(THREADS).peek(Thread::start)
                .toList();
        latch.countDown();

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(1, count.get());
    }

    @Test
    void should_run_only_once_when_threads_in_executor_service() throws ExecutionException, InterruptedException {
        AtomicReference<AtomicBooleanExample> atomicBooleanExample = new AtomicReference<>();
        AtomicInteger count = new AtomicInteger(0);

        Runnable r = () -> atomicBooleanExample.get().doOnce(count::incrementAndGet);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

        for (int repeats = 0; repeats < 1000; repeats++) {
            count.set(0);
            atomicBooleanExample.set(new AtomicBooleanExample());

            List<Future<?>> futures = Stream.generate(() -> executorService.submit(r))
                    .limit(THREADS)
                    .collect(Collectors.toList());

            for (Future<?> future : futures) {
                future.get();
            }

            assertEquals(1, count.get());
        }

        executorService.shutdown();
    }

    @Test
    void should_run_only_once_when_use_completable_future() throws ExecutionException, InterruptedException {
        AtomicReference<AtomicBooleanExample> atomicBooleanExample = new AtomicReference<>();
        AtomicInteger count = new AtomicInteger(0);

        Runnable r = () -> atomicBooleanExample.get().doOnce(count::incrementAndGet);

        for (int repeats = 0; repeats < 1000; repeats++) {
            count.set(0);
            atomicBooleanExample.set(new AtomicBooleanExample());

            List<CompletableFuture<?>> futures = Stream
                    .generate(() -> CompletableFuture.runAsync(r))
                    .limit(THREADS)
                    .collect(Collectors.toList());

            for (CompletableFuture<?> completableFuture: futures) {
                completableFuture.get();
            }

            assertEquals(1, count.get());
        }
    }

    @Test
    void should_run_only_once_when_use_completable_future_aggreagation() throws ExecutionException, InterruptedException {
        AtomicReference<AtomicBooleanExample> atomicBooleanExample = new AtomicReference<>();
        AtomicInteger count = new AtomicInteger(0);

        Runnable r = () -> atomicBooleanExample.get().doOnce(count::incrementAndGet);

        for (int repeats = 0; repeats < 1000; repeats++) {
            count.set(0);
            atomicBooleanExample.set(new AtomicBooleanExample());

            CompletableFuture<?> future = CompletableFuture.allOf(
                    Stream
                    .generate(() -> CompletableFuture.runAsync(r))
                    .limit(THREADS)
                    .toArray(CompletableFuture[]::new)
            );

            future.join();

            assertEquals(1, count.get());
        }
    }
}
