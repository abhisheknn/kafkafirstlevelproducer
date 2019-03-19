package com.micro.kafkaFirstLevelProducer.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String TOKENMANAGERURL = System.getenv("TOKENMANAGERURL");
	public static final String TOKENMANAGER_GETPUBLICKEY= "http://"+TOKENMANAGERURL+"/auth/getPublicKey";
	public static final String TOKENMANAGER_GETMACHINES = "http://"+TOKENMANAGERURL+"/auth/getMachines";

	public static Map<String, String> commonHeaders= initMap();//new HashMap<>();

	private static Map<String, String> initMap() {
		Map<String, String> commonHeaders= new HashMap<>();
		commonHeaders.put("Content-Type","application/json");
		return commonHeaders;
	}
	
	
}
