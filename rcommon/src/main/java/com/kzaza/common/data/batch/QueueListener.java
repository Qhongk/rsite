package com.kzaza.common.data.batch;

import java.util.List;
import java.util.Queue;

/**
 * Created by rick01.kong on 2015/8/21.
 */
public interface QueueListener<E> {

    void trigger(List<E> e);

    boolean hook(Queue queue);

    int getBatchSize();
}
