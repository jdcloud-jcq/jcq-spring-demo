package com.jcloud.jcq.spring.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 消费者 demo.
 *
 * @date 2018-05-17
 */
public class ConsumerDemo {

    public static void main(String[] args) throws Exception {
        // 从context中获取消费者bean （对于生命周期由spring管理的对象，比如controller、service等, 要使用consumer bean, 直接注入即可)
        JCQConsumer jcqConsumer = (JCQConsumer) new ClassPathXmlApplicationContext("consumer.xml").getBean("jcqConsumer");
    }
}
