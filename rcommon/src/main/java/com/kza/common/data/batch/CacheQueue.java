package com.kza.common.data.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by rick.kong.
 */
public class CacheQueue<T> {

    private final int batchSize;

    private final BlockingQueue<T> queue;

    private List<QueueListener> operations = new ArrayList<>();

    public CacheQueue(int batchSize, QueueListener<T> operation, BlockingQueue<T> queue) {
        this.batchSize = 100 > batchSize ? 100 : batchSize;
        this.queue = queue;
        this.operations.add(operation);
    }

    public void listener() {
        for (QueueListener operation : operations) {
            int size = operation.getBatchSize() > 0 ? operation.getBatchSize() : batchSize;
            if (queue.size() > size && operation.hook(queue)) {
                List<T> elements = new ArrayList<>(size);
                queue.drainTo(elements, size);
                operation.trigger(elements);
            }
        }
    }

    public void flush() {
        if (null != queue && queue.size() > 0) {
            List<T> elements = new ArrayList<>(queue.size());
            queue.drainTo(elements, queue.size());
            for (QueueListener operation : operations) {
                operation.trigger(elements);
            }
        }
    }

    public void put(T t) throws InterruptedException {
        queue.put(t);
        listener();
    }

    public boolean removeListener(QueueListener operation) {
        return this.operations.remove(operation);
    }

    public void addListener(QueueListener operation) {
        this.operations.add(operation);
    }
}
