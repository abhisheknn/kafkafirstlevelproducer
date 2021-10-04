package com.micro.kafkaFirstLevelProducer.rbac;

import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

  @Component
  public class MachineStatusService {

  private Map<String, Map<String, String>> activeMachines = new HashMap<>();
  public Map<String, Map<String, String>> getActiveMachines() {
		return activeMachines;
	}

    public static final String ID="MACHINESTATUS";

    private static final Logger LOGGER = LoggerFactory.getLogger(MachineStatusService.class);
    @KafkaListener(topics =  KafkaConstants.MACHINESTATUS, groupId = "MACHINESTATUS#{T(java.util.UUID).randomUUID().toString()}")
    public void receive(ConsumerRecord<String, String> record) {
      LOGGER.info("received payload='{}'", record.toString());

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
    }
  }

