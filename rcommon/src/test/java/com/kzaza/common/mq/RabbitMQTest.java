package com.kzaza.common.mq;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by rick01.kong on 2015/9/7.
 */
public class RabbitMQTest {

    public RabbitMQService rabbitMQService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSend() throws IOException {
        rabbitMQService = new RabbitMQService();
        rabbitMQService.send();
    }

    @Test
    public void testReceive() throws IOException {
        rabbitMQService = new RabbitMQService();
        rabbitMQService.receive();
    }
}