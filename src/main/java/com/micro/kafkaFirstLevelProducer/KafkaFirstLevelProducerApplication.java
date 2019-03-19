package com.micro.kafkaFirstLevelProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaFirstLevelProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaFirstLevelProducerApplication.class, args);
	}
}
