package com.takipi.tests.counters;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by life on 28/7/14.
 */
public class TrySomething {
    public static void main(String[] args) {
        StampedLock l = new StampedLock();
        long stamp1 = l.writeLock();

        long stamp2 = l.tryOptimisticRead();
        System.out.println(stamp2);
        System.out.println(l.validate(stamp2));
        l.unlockWrite(stamp1);
    }
}
