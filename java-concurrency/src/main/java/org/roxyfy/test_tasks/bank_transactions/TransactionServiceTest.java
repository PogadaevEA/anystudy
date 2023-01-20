package org.roxyfy.test_tasks.bank_transactions;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionServiceTest {

    private final TransactionService transactionService = new TransactionService();

    private final int THREADS = Runtime.getRuntime().availableProcessors();

    @Test
    void should_add_money_to_account() {
        // given
        Account account = new Account("UserName", BigDecimal.ZERO);
        CountDownLatch latch = new CountDownLatch(1);
        Runnable addMoney = () -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            transactionService.addMoneyToAccountBalance(account, BigDecimal.TEN);
        };

        // when
        CompletableFuture<?> future = CompletableFuture.allOf(
                Stream.generate(() -> CompletableFuture.runAsync(addMoney))
                        .limit(THREADS)
                        .toArray(CompletableFuture[]::new)
        );
        latch.countDown();

        future.join();

        // then
        assertEquals(BigDecimal.TEN.multiply(BigDecimal.valueOf(THREADS)), account.getBalance());
    }

    @Test
    void should_read_account_balance() {
        // given
        Account account = new Account("UserName", BigDecimal.ZERO);
        Runnable addMoney = () -> transactionService.addMoneyToAccountBalance(account, BigDecimal.TEN);

        //when

    }
}
