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
	private Producer<String, String> kafkaProducer;

	public KafkaProducerService() {
		kafkaProducer = ProducerCreator.createProducer();
	}

	private Gson gson = new Gson();

	public void send(String hostname, Map<String, Object> requestBody) {
		ProducerRecord<String, String> record =null;
		if(requestBody.get("TYPE").equals("CONTAINERINFO")) {
			record= new ProducerRecord<>(KafkaConstants.CONTAINERINFO_TOPIC_NAME, hostname,
				gson.toJson(requestBody)); 
		}else if(requestBody.get("TYPE").equals("DELETEDCONTAINERS")) {
			record= new ProducerRecord<>(KafkaConstants.DELETEDCONTAINERLIST_TOPIC_NAME, hostname,
					gson.toJson(requestBody));
		}
		
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