package com.springkafka.demo.main.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "createdBookRecord", groupId = "createdBookRecord-group")
	public void listenTo(String messageReceived) {
		System.out.println("Message received is: " + messageReceived);
	}
}
