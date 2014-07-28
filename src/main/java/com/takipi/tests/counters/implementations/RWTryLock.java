package com.takipi.tests.counters.implementations;

import com.takipi.tests.counters.Counter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWTryLock implements Counter
{
	private ReadWriteLock rwlock = new ReentrantReadWriteLock();
	
	private Lock rlock = rwlock.readLock();
	private Lock wlock = rwlock.writeLock();
	
	private long counter;
	
	public long getCounter()
	{
		try
		{
            try {
                rlock.tryLock(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return counter;
		}
		finally
		{
			rlock.unlock();
		}
	}
	
	public void increment(long amount)
	{
		try
		{
            try {
			    wlock.tryLock(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter+=amount;
		}
		finally
		{
			wlock.unlock();
		}
	}
}
