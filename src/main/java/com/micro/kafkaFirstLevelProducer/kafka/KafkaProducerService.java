package com.micro.kafkaFirstLevelProducer.kafka;

import java.lang.reflect.Type;
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
		ProducerRecord<String, String> record =null;
		if(requestBody.get("TYPE").equals("CONTAINERINFO")) {
			record= new ProducerRecord<>(KafkaConstants.CONTAINERINFO_TOPIC_NAME, key,
				gson.toJson(requestBody.get("value"))); 
		}else if(requestBody.get("TYPE").equals("CONTAINERLIST")) {
			record= new ProducerRecord<>(KafkaConstants.CONTAINERLIST_TOPIC_NAME, key,
				gson.toJson(requestBody.get("value"))); 
		}else if(requestBody.get("TYPE").equals("DELETEDCONTAINERS")) {
			record= new ProducerRecord<>(KafkaConstants.DELETEDCONTAINERLIST_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
		}else if(requestBody.get("TYPE").equals("FILE_DIFF")) {
			record= new ProducerRecord<>(KafkaConstants.FILE_DIFF_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
		}else if(requestBody.get("TYPE").equals("COMMANDS")) {
			record= new ProducerRecord<>(KafkaConstants.COMMANDS_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
		}else if(requestBody.get("TYPE").equals("PROCESSES")) {
			record= new ProducerRecord<>(KafkaConstants.PROCESSES_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
		}else if(((String)requestBody.get("TYPE")).contains("NETWORK_LIST")) {
			record= new ProducerRecord<>(KafkaConstants.NETWORK_LIST_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
		}else if(((String)requestBody.get("TYPE")).equals("NETWORK_CREATE") || ((String)requestBody.get("TYPE")).equals("NETWORK_CONNECT")||((String)requestBody.get("TYPE")).equals("NETWORK_DISCONNECT")) {
			record= new ProducerRecord<>(KafkaConstants.NETWORK_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
		}else if(((String)requestBody.get("TYPE")).equals("NETWORK_DESTROY")|| ((String)requestBody.get("TYPE")).equals("NETWORK_REMOVE")) {
			record= new ProducerRecord<>(KafkaConstants.NETWORK_DELETED_TOPIC_NAME, key,
					gson.toJson(requestBody.get("value")));
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
