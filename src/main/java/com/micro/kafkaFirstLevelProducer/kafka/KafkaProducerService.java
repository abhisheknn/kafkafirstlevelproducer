package com.micro.kafkaFirstLevelProducer.kafka;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;

@Component
public class KafkaProducerService {
	private Producer<String, String> kafkaProducer;

	public KafkaProducerService() {
		kafkaProducer = ProducerCreator.createProducer();
	}

	private Gson gson = new Gson();
    Type  mapType= new TypeToken<Map<String,Object>>(){}.getType();
    Type listType= new TypeToken<List<Map<String,Object>>>(){}.getType();
	public void send(String key, Map<String, Object> requestBody) {
		
		try {
			ProducerRecord<String, String> record= new ProducerRecord<String, String>(KafkaConstants.TOPICS.get(requestBody.get("TYPE")),key, gson.toJson(requestBody.get("value")));
			kafkaProducer.send(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		kafkaProducer.flush();
		System.out.println("Record flushed");
	}
}
