package com.micro.kafkaFirstLevelProducer.kafka;

import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;import java.util.UUID;

@Component
public class KafkaConsumerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
  @KafkaListener(topics = KafkaConstants.MACHINESTATUS, id = "MACHINESTATUS" )
  public void receive(ConsumerRecord<?, ?> consumerRecord) {
    LOGGER.info("received payload='{}'", consumerRecord.toString());
  }
}
