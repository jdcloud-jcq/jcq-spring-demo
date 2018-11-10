package com.jcloud.jcq.spring.demo.producer;

import com.jcloud.jcq.protocol.Message;
import com.jcloud.jcq.sdk.producer.GlobalOrderProducer;
import com.jcloud.jcq.sdk.producer.model.SendBatchResult;
import com.jcloud.jcq.sdk.producer.model.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局顺序消息生产者 demo.
 *
 * @date 2018-05-17
 */
public class GlobalOrderProducerDemo {
    private static final Logger logger = LoggerFactory.getLogger(GlobalOrderProducerDemo.class);
    /**
     * topic名称
     */
    private static final String TOPIC = "testTopic";

    public static void main(String[] args) throws Exception {
        // 从context中获取全局顺序消息生产者bean （对于生命周期由spring管理的对象，比如controller、service等, 要使用globalOrderProducer bean, 直接注入即可)
        GlobalOrderProducer globalOrderProducer = (GlobalOrderProducer) new ClassPathXmlApplicationContext("global-order-producer.xml").getBean("globalOrderProducer");

        // 创建message, 全局顺序消息不支持tag及延迟投递属性设置
        Message message = new Message();
        message.setTopic(TOPIC);
        message.setBody(("this is message boy").getBytes());
        Message message1 = new Message();
        message1.setTopic(TOPIC);
        message1.setBody(("this is message1 boy").getBytes());

        // 同步发送单条消息
        SendResult sendResult = globalOrderProducer.sendMessage(message);
        logger.info("messageId:{}, resultCode:{}", sendResult.getMessageId(), sendResult.getResultCode());

        // 同步发送批量消息, 一批最多32条消息
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);
        SendBatchResult sendBatchResult = globalOrderProducer.sendBatchMessage(messages);
        logger.info("messageIds:{}, resultCode:{}", sendBatchResult.getMessageIds(), sendBatchResult.getResultCode());
    }
}
