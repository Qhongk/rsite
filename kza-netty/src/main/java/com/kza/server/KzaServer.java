package com.kza.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kza on 2015/11/10.
 */
public class KzaServer {

    private static final AtomicInteger i = new AtomicInteger(1);
    private static final int bossGroupSize = Integer.getInteger("server.boss.thread.size", Runtime.getRuntime().availableProcessors());
    private static final int workerGroupSize = Integer.getInteger("server.worker.thread.size", Runtime.getRuntime().availableProcessors() * 2);

    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupSize, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "KZA-BossThread-" + i.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    });
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupSize, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "KZA-WorkerThread-" + i.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    });

    public void start(final int port, final ChannelInitializer<? extends Channel> pipelineFactory) {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
    }
}
