package com.jcloud.jcq.spring.demo.producer;

import com.jcloud.jcq.common.constants.MessageConstants;
import com.jcloud.jcq.protocol.Message;
import com.jcloud.jcq.sdk.producer.Producer;
import com.jcloud.jcq.sdk.producer.async.AsyncSendBatchCallback;
import com.jcloud.jcq.sdk.producer.async.AsyncSendCallback;
import com.jcloud.jcq.sdk.producer.model.SendBatchResult;
import com.jcloud.jcq.sdk.producer.model.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通消息生产者 demo.
 *
 * @date 2018-05-17
 */
public class ProducerDemo {
    private static final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
    /**
     * topic名称
     */
    private static final String TOPIC = "testTopic";

    public static void main(String[] args) throws Exception {
        // 从context中获取生产者bean （对于生命周期由spring管理的对象，比如controller、service等, 要使用producer bean, 直接注入即可)
        Producer producer = (Producer) new ClassPathXmlApplicationContext("producer.xml").getBean("producer");

        // 创建message
        Message message = new Message();
        message.setTopic(TOPIC);
        message.setBody(("this is message boy").getBytes());
        Message message1 = new Message();
        message1.setTopic(TOPIC);
        message1.setBody(("this is message1 boy").getBytes());

        // 设置message tag属性, 如有需要
        message.getProperties().put(MessageConstants.PROPERTY_TAGS, "TAG1");

        // 设置message 延迟投递属性(单位second)，如有需要
        message.getProperties().put(MessageConstants.PROPERTY_DELAY_TIME, "1000");

        // 同步发送单条消息
        SendResult sendResult = producer.sendMessage(message);
        logger.info("messageId:{}, resultCode:{}", sendResult.getMessageId(), sendResult.getResultCode());

        // 异步发送单条消息
        producer.sendMessageAsync(message, new AsyncSendCallback() {
            @Override
            public void onResult(SendResult sendResult) {
                logger.info("messageId:{}, resultCode:{}", sendResult.getMessageId(), sendResult.getResultCode());
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("exception:{}", throwable);
            }
        });

        // 同步发送批量消息, 一批最多32条消息
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);
        SendBatchResult sendBatchResult = producer.sendBatchMessage(messages);
        logger.info("messageIds:{}, resultCode:{}", sendBatchResult.getMessageIds(), sendBatchResult.getResultCode());

        // 异步发送批量消息，一批最多32条消息
        producer.sendBatchMessageAsync(messages, new AsyncSendBatchCallback() {
            @Override
            public void onResult(SendBatchResult sendBatchResult) {
                logger.info("messageIds:{}, resultCode:{}", sendBatchResult.getMessageIds(), sendBatchResult.getResultCode());
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("exception:{}", throwable);
            }
        });
    }
}
