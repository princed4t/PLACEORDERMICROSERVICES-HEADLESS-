package com.jlcindiabookstore;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class Placeorderconfig implements WebMvcConfigurer {
	// Order Message
	//A. Order Exchange
	@Bean(name="myOrderExchange")
	Exchange createOrderExchange() {
	return ExchangeBuilder.topicExchange("myorder.exchange").build();
	}
	//B. Order Queue
	@Bean(name="myOrderQueue")
	Queue createOrderQueue() {
	return QueueBuilder.durable("myorder.queue").build();
	}
	//C. Order Binding
	@Bean
	Binding orderBinding(Queue myOrderQueue, TopicExchange myOrderExchange) {
	return BindingBuilder.bind(myOrderQueue).to(myOrderExchange).with("myorder.key");
	}
	
	
	


}
