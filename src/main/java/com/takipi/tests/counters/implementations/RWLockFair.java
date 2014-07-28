package com.takipi.tests.counters.implementations;

import com.takipi.tests.counters.Counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockFair implements Counter
{
	private ReadWriteLock rwlock = new ReentrantReadWriteLock(true);
	
	private Lock rlock = rwlock.readLock();
	private Lock wlock = rwlock.writeLock();
	
	private long counter;
	
	public long getCounter()
	{
		try
		{
			rlock.lock();		
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
			wlock.lock();		
			counter+=amount;
		}
		finally
		{
			wlock.unlock();
		}
	}
}
