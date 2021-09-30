package com.micro.kafkaFirstLevelProducer.kafka;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducerService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  private Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    Type  mapType= new TypeToken<Map<String,Object>>(){}.getType();
    Type listType= new TypeToken<List<Map<String,Object>>>(){}.getType();
	public void send(String kafkaKey, Map<String, Object> requestBody) {
		try {
			String key =(String)requestBody.get("key");
			if(KafkaConstants.TOPICS.get(requestBody.get("TYPE")).equals(KafkaConstants.CONTAINERINFO_TOPIC_NAME)) {
				((Map<String, Object>)requestBody.get("value")).put("macaddress", key);
				}

			if(KafkaConstants.TOPICS.get(requestBody.get("TYPE")).equals(KafkaConstants.NETWORK_TOPIC_NAME)) {
				((Map<String, Object>)requestBody.get("value")).put("macaddress", key);
			}
			//ProducerRecord<String, String> record= new ProducerRecord<String, String>(KafkaConstants.TOPICS.get(requestBody.get("TYPE")),key, gson.toJson(requestBody.get("value")));
      sendMessage(KafkaConstants.TOPICS.get(requestBody.get("TYPE")),key,gson.toJson(requestBody.get("value")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  public void sendMessage(String topic,String key,String message) {

    ListenableFuture<SendResult<String, String>> future =
      kafkaTemplate.send(topic, message);

    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

      @Override
      public void onSuccess(SendResult<String, String> result) {
        System.out.println("Sent message=[" + message +
          "] with offset=[" + result.getRecordMetadata().offset() + "]");
      }
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message=["
          + message + "] due to : " + ex.getMessage());
      }
    });
  }
}
