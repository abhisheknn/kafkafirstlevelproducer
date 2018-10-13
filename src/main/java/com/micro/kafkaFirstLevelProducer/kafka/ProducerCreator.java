package com.micro.kafkaFirstLevelProducer.kafka;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;

public class ProducerCreator {

	public static Producer<String, String> createProducer() {
		Properties props = new Properties();
		if(KafkaConstants.KAFKA_BROKERS!=null)
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
		else
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.22.33.44:34556");	
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(props);
	}
}