package com.kza.common.thread;

import java.util.concurrent.*;

/**
 * Created by kza on 2015/10/8.
 */
public class CDL {

    private static final int N = 10;
    private static CountDownLatch cdl = new CountDownLatch(N);
    private static CyclicBarrier cb = new CyclicBarrier(N, new Runnable() {
        @Override
        public void run() {
            System.out.println("main thread end !!!");
        }
    });

    public static void main(String args[]) throws InterruptedException, BrokenBarrierException {

        ExecutorService es = Executors.newFixedThreadPool(N);
        for (int i = 0; i < N; i++) {
            es.execute(new Driver(cdl, i));
        }

        cdl.await();
        System.out.println("main thread end !!!");


//        System.out.println("cb.getParties() : " + cb.getParties());
//        for (int i = 0; i < N-2; i++) {
//            es.execute(new DriverB(cb, i));
//        }
//        Thread.sleep(2000);
//        System.out.println("cb.getNumberWaiting():" + cb.getNumberWaiting());
//        cb.await();
        es.shutdown();
    }

    private static class Driver implements Runnable {

        private CountDownLatch cdl;
        private int num;

        public Driver(CountDownLatch cdl, int num) {
            this.cdl = cdl;
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("This is Thread " + num);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdl.countDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + num + ", end!");
        }
    }



    private static class DriverB implements Runnable {

        private CyclicBarrier cb;
        private int num;

        public DriverB(CyclicBarrier cb, int num) {
            this.cb = cb;
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println("This is Thread " + num + ", cb.getNumberWaiting():" + cb.getNumberWaiting());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + num + ", end!, cb.getNumberWaiting():" + cb.getNumberWaiting());
        }
    }
}
