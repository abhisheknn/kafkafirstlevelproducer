package com.micro.kafkaFirstLevelProducer.kafka.constants;

public class KafkaConstants {
	public static String KAFKA_BROKERS = System.getenv("KAFKABROKERS");
	public static String CLIENT_ID = "client1";
	public static String CONTAINERINFO_TOPIC_NAME = "container_details";
	public static String DELETEDCONTAINERLIST_TOPIC_NAME = "deleted_containerid_list";
	public static String OFFSET_RESET_LATEST = "latest";
	public static String OFFSET_RESET_EARLIER = "earliest";
	public static Integer MAX_POLL_RECORDS = 1;
	public static final String JWTOKEN = "jwtoken";
}
