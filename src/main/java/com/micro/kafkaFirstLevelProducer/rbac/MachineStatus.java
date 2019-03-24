package com.micro.kafkaFirstLevelProducer.rbac;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import com.micro.kafka.KafkaConsumer;
import com.micro.kafkaFirstLevelProducer.constants.Constants;
import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;

@Component
public class MachineStatus {

	private Map<String, Map<String, String>> activeMachines = new HashMap<>();

	public Map<String, Map<String, String>> getActiveMachines() {
		return activeMachines;
	}

	
	public MachineStatus() {

		KafkaConsumer kafkaConsumer = new KafkaConsumer();
		Properties config = new Properties();
		config.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.MACHINESTATUSGROUPID);
		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
		

		kafkaConsumer.build().withConfig(config).withTopic(Constants.MACHINESTATUS).withProcessor(() -> {
			ConsumerRecords<String, String> records;
			records = kafkaConsumer.builder.getConsumer().poll(100);
			for (ConsumerRecord<String, String> record : records) {
				String key = record.key();
				String value = record.value();
				String[] keys = null;
				if (null != key && key.contains("_")) {
					keys = key.split("_");
				}
				String tenantId = keys[0];
				String macaddress = keys[1];
				Map<String, String> machines = this.activeMachines.get(tenantId);
				if(null==machines || machines.isEmpty()) {
					machines= new HashMap<>();
					this.activeMachines.put(tenantId, machines);
				}
				
				machines.put(macaddress, value);
				kafkaConsumer.builder.getConsumer().commitSync();
			}
		}).consume();

	}

}
