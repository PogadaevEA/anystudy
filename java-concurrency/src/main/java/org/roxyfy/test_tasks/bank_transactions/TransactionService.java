package org.roxyfy.test_tasks.bank_transactions;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionService {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void addMoneyToAccountBalance(Account account, BigDecimal addedMoney) {
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            account.setBalance(account.getBalance().add(addedMoney));
        } finally {
            writeLock.unlock();
        }
    }

    public BigDecimal readAccountBalance(Account account) {
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();
        try {
            return account.getBalance();
        } finally {
            readLock.unlock();
        }
    }
}
