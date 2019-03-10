package com.micro.kafkaFirstLevelProducer.kafka.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KafkaConstants {
	public static final String KAFKA_BROKERS = System.getenv("KAFKABROKERS");
	public static final String CLIENT_ID = "client1";
	public static final  String CONTAINERINFO_TOPIC_NAME = "dockerx.container_details";
	public static final String DELETEDCONTAINERLIST_TOPIC_NAME = "dockerx.deleted_containerid";
	public static final String OFFSET_RESET_LATEST = "latest";
	public static final String OFFSET_RESET_EARLIER = "earliest";
	public static final Integer MAX_POLL_RECORDS = 1;
	public static final String JWTOKEN = "jwtoken";
	public static final String FILE_DIFF_TOPIC_NAME = "dockerx.file_diff";
	public static final String COMMANDS_TOPIC_NAME = "dockerx.commands";
	public static final String PROCESSES_TOPIC_NAME = "dockerx.processes";
	public static final String CONTAINERLIST_TOPIC_NAME = "dockerx.container_list";
	public static final String NETWORK_LIST_TOPIC_NAME = "dockerx.network_list";
	public static final String NETWORK_TOPIC_NAME = "dockerx.network_details";
	public static final String NETWORK_DELETED_TOPIC_NAME = "dockerx.deleted_networkid";
	
	public static final Map<String, String> TOPICS= initMap();
	private static final String VOLUME_TOPIC_NAME = "dockerx.volume_details";
	private static final String VOLUME_DELETED_TOPIC_NAME = "dockerx.deleted_volumeid";
	private static final String VOLUMELIST_TOPIC_NAME = "dockerx.volume_list";
	
	private static Map<String, String> initMap() {
	Map<String, String> map= new HashMap<>();
	map.put("CONTAINERINFO", KafkaConstants.CONTAINERINFO_TOPIC_NAME);
	map.put("CONTAINERLIST", KafkaConstants.CONTAINERLIST_TOPIC_NAME);
	map.put("DELETEDCONTAINERS", KafkaConstants.DELETEDCONTAINERLIST_TOPIC_NAME);
	map.put("FILE_DIFF", KafkaConstants.FILE_DIFF_TOPIC_NAME);
	map.put("COMMANDS", KafkaConstants.COMMANDS_TOPIC_NAME);
	map.put("PROCESSES", KafkaConstants.PROCESSES_TOPIC_NAME);
	map.put("NETWORK_LIST", KafkaConstants.NETWORK_LIST_TOPIC_NAME);
	map.put("NETWORK_CREATE", KafkaConstants.NETWORK_TOPIC_NAME);
	map.put("NETWORK_CONNECT", KafkaConstants.NETWORK_TOPIC_NAME);
	map.put("NETWORK_DISCONNECT", KafkaConstants.NETWORK_TOPIC_NAME);
	map.put("NETWORK_DESTROY", KafkaConstants.NETWORK_DELETED_TOPIC_NAME);
	map.put("VOLUME_CREATE", KafkaConstants.VOLUME_TOPIC_NAME);
	map.put("VOLUME_MOUNT", KafkaConstants.VOLUME_TOPIC_NAME);
	map.put("VOLUME_DESTROY", KafkaConstants.VOLUME_DELETED_TOPIC_NAME);
	map.put("VOLUME_UNMOUNT", KafkaConstants.VOLUME_TOPIC_NAME);
	map.put("VOLUMELIST", KafkaConstants.VOLUMELIST_TOPIC_NAME);
	return Collections.unmodifiableMap(map);
	}
}
