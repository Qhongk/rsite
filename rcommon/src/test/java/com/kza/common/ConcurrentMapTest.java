package com.kza.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by kza on 2015/11/17.
 */
public class ConcurrentMapTest {

    public static void main(String args[]) throws ExecutionException {
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> c = new ConcurrentHashMap<>();

        LoadingCache<Integer, Integer> loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, Integer>() {
            @Override
            public Integer load(Integer integer) throws Exception {
                return integer;
            }
        });

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            map.put(i, i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            c.put(i, i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

        // guava cache
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            loadingCache.put(i, i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

        
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            map.get(i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            c.get(i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            loadingCache.get(i);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }
}
