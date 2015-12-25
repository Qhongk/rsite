package com.kza.common.data.batch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by rick01.kong on 2015/12/25.
 */
public class Cleanser {

    private ThreadGroup group = new ThreadGroup("Cleanser-Thread-Group");

    public static void newCleanser(Runnable runnable) {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
        ses.scheduleWithFixedDelay(runnable, 1000, 5, TimeUnit.SECONDS);
    }
}