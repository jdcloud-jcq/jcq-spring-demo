package com.jcloud.jcq.spring.demo.consumer;

import com.jcloud.jcq.client.consumer.ConsumeResult;
import com.jcloud.jcq.client.consumer.MessageListener;
import com.jcloud.jcq.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 默认MessageListener.
 *
 * @date 2018-07-18
 */
public class DefaultMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageListener.class);

    @Override
    public ConsumeResult consumeMessages(List<Message> list) {
        logger.info("messages:{}", list);
        return ConsumeResult.SUCCESS;
    }
}
