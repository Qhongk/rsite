package com.kza.common.thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kza on 2015/9/22.
 */
public class ScheduledExecutorServiceTest {

    private static class WorkerThread implements Runnable {

        private String command;

        public WorkerThread(String s) {
            this.command = s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
        }

        private void processCommand() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return this.command;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        //schedule to run after sometime
        System.out.println("Current Time = " + new Date());
        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            WorkerThread worker = new WorkerThread("do heavy processing");
            scheduledThreadPool.schedule(worker, 10, TimeUnit.SECONDS);
        }

        //add some delay to let some threads spawn by scheduler
//        Thread.sleep(30000);

        scheduledThreadPool.shutdown();
        while (!scheduledThreadPool.isTerminated()) {
            //wait for all tasks to finish
            Thread.sleep(1);
        }
        System.out.println("Current Time = " + new Date());
        System.out.println("Finished all threads");
    }
}
