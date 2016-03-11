package com.takipi.tests.counters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.takipi.tests.counters.implementations.*;

public class Main
{
	public static long TARGET_NUMBER 	= 200000000l;
	public static int RTHREADS 			= 4;
    public static int MODULO 			= 2;
	public static int ROUNDS 			= 10;
	private static String COUNTER 		= Counters.DIRTY.toString();
	
    private static ExecutorService ers;
	
	private static int round;
	private static long start;
    private static long endTm;
	
	private static Boolean[] rounds;
	
	private static enum Counters
	{
		DIRTY,
		VOLATILE,
		SYNCHRONIZED,
        RWLOCK,
        TRYLOCK,
        FAIR,
		ATOMIC,
		ADDER,
		STAMPED,
        STAMPED0,
        STAMPED3,
        STAMPED5,
	}
	
	public static void main(String[] args)
	{
		COUNTER = Counters.DIRTY.toString();
		
		if (args.length > 0)
		{
			COUNTER = args[0];
		}
		
		if (args.length > 1)
		{
			RTHREADS = Integer.valueOf(args[1]);
		}

        if (args.length > 2)
        {
            MODULO = Integer.valueOf(args[2]);
        }
		
		if (args.length > 3)
		{
			ROUNDS = Integer.valueOf(args[3]);
		}
		
		if (args.length > 4)
		{
			TARGET_NUMBER = Long.valueOf(args[4]);
		}
		
		rounds = new Boolean[ROUNDS];
		
		System.out.println("@@@ Using " + COUNTER + ". rthreads: " + RTHREADS + " modulo: " + MODULO + ". rounds: " + ROUNDS +
				". Target: " + TARGET_NUMBER);


        System.out.println("warmup start");
        for (round = 0; round < 5; round++)     // warmup
        {
            rounds[round] = Boolean.FALSE;

            Counter counter = getCounter();

            ers = Executors.newFixedThreadPool(RTHREADS);


            start = System.currentTimeMillis();

            for (int j = 0; j < RTHREADS; j++)
            {
                ers.execute(new ReaderWriter(counter,MODULO));

            }


            try
            {
                ers.awaitTermination(10, TimeUnit.MINUTES);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("warmup end");
        long acc = 0;
        for (round = 0; round < ROUNDS; round++)
		{
			rounds[round] = Boolean.FALSE;
			
			Counter counter = getCounter();
			
			ers = Executors.newFixedThreadPool(RTHREADS);

			
			start = System.currentTimeMillis();
			
			for (int j = 0; j < RTHREADS; j++)
			{	
				ers.execute(new ReaderWriter(counter,MODULO));

			}

			
			try
			{
		        ers.awaitTermination(10, TimeUnit.MINUTES);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
            acc += (endTm-start);
		}
        System.out.println("@@@ AVG = "+acc/ROUNDS);
    }
	
	public static Counter getCounter()
	{
		Counters counterTypeEnum = Counters.valueOf(COUNTER);
		
		switch (counterTypeEnum)
		{
			case ADDER:
				return new Adder();
			case ATOMIC:
				return new Atomic();
			case DIRTY:
				return new Dirty();
            case RWLOCK:
                return new RWLock();
            case TRYLOCK:
                return new RWTryLock();
            case FAIR:
                return new RWLockFair();
			case SYNCHRONIZED:
				return new Synchronized();
			case VOLATILE:
				return new Volatile();
            case STAMPED0:
                return new Stamped(0);
            case STAMPED:
                return new Stamped();
            case STAMPED3:
                return new Stamped(3);
            case STAMPED5:
                return new Stamped(5);
		}
		
		return null;
	}
	
	public static void  publish(long end)
	{

		synchronized (rounds[round])
		{
			if (rounds[round] == Boolean.FALSE)
			{
                endTm=end;
				System.out.println(end-start);
				
				rounds[round] = Boolean.TRUE;
				
				ers.shutdownNow();
			}
		}
	}
}
