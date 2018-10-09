package com.micro.kafkaFirstLevelProducer.kafka;

import java.util.Map;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;

@Component
public class KafkaProducerService {
Producer<String, String> kafkaProducer;
public KafkaProducerService() {
	 kafkaProducer= ProducerCreator.createProducer();
}

	public void send(String hostname,String requestBody) {
		ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_NAME,hostname,requestBody);  // take the topic name from config server
		try {
			kafkaProducer.send(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		kafkaProducer.flush();
		System.out.println("Record flushed");
	}
}
