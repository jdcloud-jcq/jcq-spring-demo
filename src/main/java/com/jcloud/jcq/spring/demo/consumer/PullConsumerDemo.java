package com.jcloud.jcq.spring.demo.consumer;

import com.jcloud.jcq.common.filter.FilterExpression;
import com.jcloud.jcq.common.message.AckAction;
import com.jcloud.jcq.sdk.consumer.PullConsumer;
import com.jcloud.jcq.sdk.consumer.async.AsyncAckCallback;
import com.jcloud.jcq.sdk.consumer.async.AsyncPullCallback;
import com.jcloud.jcq.sdk.consumer.model.AckResult;
import com.jcloud.jcq.sdk.consumer.model.PullResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 拉取型消费者 demo.
 *
 * @date 2018-05-17
 */
public class PullConsumerDemo {
    private static final Logger logger = LoggerFactory.getLogger(PullConsumerDemo.class);
    /**
     * topic名称
     */
    private static final String TOPIC = "testTopic";

    public static void main(String[] args) throws Exception {
        // 从context中获取消费者bean （对于生命周期由spring管理的对象，比如controller、service等, 要使用consumer bean, 直接注入即可)
        PullConsumer pullConsumer = (PullConsumer) new ClassPathXmlApplicationContext("pull-consumer.xml").getBean("pullConsumer");

        // 创建消费过滤条件，如果需要
        FilterExpression filterExpression = new FilterExpression();
        filterExpression.setExpressionType(FilterExpression.ExpressionType.TAG);
        filterExpression.setExpression("TAG1,TAG2");

        // 同步拉取消息, 当需要指定tag作为过滤条件时，第二个参数填充具体的filterExpression
        PullResult pullResult = pullConsumer.pullMessage(TOPIC, null);
        logger.info("Sync pullResult.resultCode:{}, pullResult.ackIndex:{}, pullResult.messages:{}",
                pullResult.getResultCode(), pullResult.getAckIndex(), pullResult.getMessages());

        // 异步拉取消息,当需要指定tag作为过滤条件时，第二个参数填充具体的filterExpression
        pullConsumer.pullMessageAsync(TOPIC, null, new AsyncPullCallback() {
            @Override
            public void onResult(PullResult pullResult) {
                logger.info("Async pullResult.resultCode:{}, pullResult.ackIndex:{}, pullResult.messages:{}",
                        pullResult.getResultCode(), pullResult.getAckIndex(), pullResult.getMessages());
            }

            @Override
            public void onException(Throwable throwable) {
            }
        });

        // 同步ack消息
        AckResult ackResult = pullConsumer.ackMessage(TOPIC, pullResult.getAckIndex(), AckAction.SUCCESS);
        logger.info("Sync ackResult:{}", ackResult.getResultCode());

        // 异步ack消息
        pullConsumer.ackMessageAsync(TOPIC, pullResult.getAckIndex(), AckAction.SUCCESS, new AsyncAckCallback() {
            @Override
            public void onResult(AckResult ackResult) {
                logger.info("Async ackResult:{}", ackResult.getResultCode());
            }

            @Override
            public void onException(Throwable throwable) {
            }
        });
    }
}
