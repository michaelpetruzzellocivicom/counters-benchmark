package com.takipi.tests.counters;

/**
 * Created by life on 27/7/14.
 */
public class ReaderWriter implements Runnable {
    private final Counter counter;
    private int modulo ;
    private long innerCounter = 0;

    public ReaderWriter(Counter counter,int modulo)
    {
        this.counter = counter;
        this.modulo = modulo;
    }

    public void run()
    {
        while (true)
        {
            if (Thread.interrupted())
            {
                break;
            }
            if ((innerCounter % modulo) != 0) {
                long count = counter.getCounter();

                if (count > Main.TARGET_NUMBER) {
                    Main.publish(System.currentTimeMillis());
                    break;
                }
            }  else {
                counter.increment(modulo);
            }
            innerCounter++;
        }
    }
}
