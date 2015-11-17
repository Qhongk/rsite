package com.kza.common.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by kza on 2015/9/24.
 */
public class YieldExample {
    public static void main(String[] args)
    {
        Thread producer = new Producer();
        Thread consumer = new Consumer("1");
        Thread consumer2 = new Consumer("2");

        producer.setPriority(Thread.MIN_PRIORITY); //Min Priority
        consumer.setPriority(Thread.MAX_PRIORITY); //Max Priority
        consumer2.setPriority(Thread.MAX_PRIORITY); //Max Priority

        consumer.start();
        producer.start();
        consumer2.start();
    }
}

class Producer extends Thread
{
    public void run()
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println("I am Producer : Produced Item " + i);
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }
}

class Consumer extends Thread
{
    private String name;
    public Consumer(String name) {
        this.name = name;
    }

    public void run()
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println("I am Consumer" + name + " : Consumed Item " + i);
            try {
                TimeUnit.MICROSECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }
}