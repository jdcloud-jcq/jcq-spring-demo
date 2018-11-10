package com.jcloud.jcq.spring.demo.consumer;

import com.jcloud.jcq.client.Exception.ClientException;
import com.jcloud.jcq.client.consumer.MessageListener;
import com.jcloud.jcq.common.filter.FilterExpression;
import com.jcloud.jcq.sdk.consumer.Consumer;

import java.util.HashMap;
import java.util.Map;

/**
 * Consumer的bean封装，用于spring.
 *
 * @date 2018-07-18
 */
public class JCQConsumer {
    /**
     * sdk的consumer对象
     */
    private Consumer consumer;
    /**
     * topic和MessageListener的map
     */
    private Map<String, MessageListener> topicMessageListenerMap = new HashMap<>();
    /**
     * topic和FilterExpression的map
     */
    private Map<String, FilterExpression> topicFilterExpressionMap = new HashMap<>();

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Map<String, MessageListener> getTopicMessageListenerMap() {
        return topicMessageListenerMap;
    }

    public void setTopicMessageListenerMap(Map<String, MessageListener> topicMessageListenerMap) {
        this.topicMessageListenerMap = topicMessageListenerMap;
    }

    public Map<String, FilterExpression> getTopicFilterExpressionMap() {
        return topicFilterExpressionMap;
    }

    public void setTopicFilterExpressionMap(Map<String, FilterExpression> topicFilterExpressionMap) {
        this.topicFilterExpressionMap = topicFilterExpressionMap;
    }

    public void start() throws ClientException {
        for (Map.Entry<String, MessageListener> entry : topicMessageListenerMap.entrySet()) {
            String topic = entry.getKey();
            MessageListener messageListener = entry.getValue();
            consumer.subscribeTopic(topic, messageListener, topicFilterExpressionMap.get(topic));
        }
        consumer.start();
    }

    public void shutdown() throws ClientException {
        consumer.shutdown();
    }
}
