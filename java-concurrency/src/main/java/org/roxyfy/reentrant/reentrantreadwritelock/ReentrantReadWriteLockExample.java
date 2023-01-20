package org.roxyfy.reentrant.reentrantreadwritelock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {

    public final List<String> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public String get(int index) {
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        readLock.lock();
        try {
            return list.get(index);
        } finally {
            readLock.unlock();
        }
    }

    public void add(String value) {
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            list.add(value);
        } finally {
            writeLock.unlock();
        }
    }

    public List<String> getList() {
        return list;
    }
}
