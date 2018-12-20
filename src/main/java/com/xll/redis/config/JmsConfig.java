package com.xll.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.jms.ConnectionFactory;
import java.util.concurrent.Executors;

/**
 * @author xielulin
 * @create 2018-11-29 22:08
 * @desc JMS配置信息
 **/
@Configuration
@EnableJms
//@EnableRedisHttpSession
public class JmsConfig {
    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactory);
        factory.setTaskExecutor(Executors.newFixedThreadPool(6));
//        factory.setConcurrency("6");
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        factory.setTaskExecutor(Executors.newFixedThreadPool(6));
//        factory.setConcurrency("6");
        return factory;
    }
}

