package com.micro.kafkaFirstLevelProducer.kafka.constants;

public class KafkaConstants {
	public static String KAFKA_BROKERS = System.getenv("KAFKABROKERS");
	public static String CLIENT_ID = "client1";
	public static String TOPIC_NAME = System.getenv("TOPICNAME");
	//public static String GROUP_ID_CONFIG = System.getenv("GROUP");
	public static String OFFSET_RESET_LATEST = "latest";
	public static String OFFSET_RESET_EARLIER = "earliest";
	public static Integer MAX_POLL_RECORDS = 1;
}
