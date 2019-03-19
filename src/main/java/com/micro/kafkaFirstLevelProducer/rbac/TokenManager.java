package com.micro.kafkaFirstLevelProducer.rbac;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.micro.client.Response;
import com.micro.jwt.JWT;
import com.micro.kafkaFirstLevelProducer.client.RestClient;
import com.micro.kafkaFirstLevelProducer.constants.Constants;

import io.jsonwebtoken.Claims;

@Component
public class TokenManager {

	@Autowired
	private RestClient restClient;

	@Autowired
	private JWT jwt;

	private String publicKey;

	private Map<String, Map<String, String>> activeMachines = new HashMap<>();
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Type mapType = new TypeToken<Map<String, String>>() {
	}.getType();
	Set<String> tenants = new HashSet<>();

	@Scheduled(fixedRate = 10000)
	public void getPublicKey() {
		Response publicKey= restClient.doGet(Constants.TOKENMANAGER_GETPUBLICKEY, Constants.commonHeaders);
		 this.publicKey =publicKey.getEntity();
	}

	//@Scheduled(fixedRate = 20000)
	private void getActiveMachines() {
		Response reponse = null;
		synchronized (this) {
		for (String tenant : tenants) {
			try {
				reponse = restClient.doGet(Constants.TOKENMANAGER_GETMACHINES + "?tenantid=" + tenant,
						Constants.commonHeaders);
			} catch (Exception e) {

			}
			if (reponse != null) {
				try {
					this.activeMachines.put(tenant, gson.fromJson(reponse.getEntity(), mapType)) ;	
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}
			}
			}
		}
	}

	public boolean verify(String tenantid, String macaddress, String token) {
		String machineStatus = null;
		machineStatus = getMachineStatus(tenantid, macaddress);
		Claims claims = jwt.verifyAndGetClaims(token, this.publicKey);
		boolean status = false;
		if (!machineStatus.equals("inactive") && null != claims) {
			if (tenantid.equals(claims.get("tenantid")) && macaddress.equals(claims.get("macaddress"))) {
				status = true;
			} else {
				killAgent();
			}
		} else {
			killAgent();
		}
		return status;
	}

	private String getMachineStatus(String tenantid, String macaddress) {
		String machineStatus=null;
		if(!tenants.contains(tenantid)) tenants.add(tenantid);
		Map<String, String> machines = this.activeMachines.get(tenantid);
		if (null == machines) {
			machineStatus = getStatus(tenantid, macaddress);
		}else {
			machineStatus = machines.get(macaddress);
			if(null==machineStatus) {
				machineStatus = getStatus(tenantid, macaddress);
			}
		}
		return machineStatus;
	}

	private String getStatus(String tenantid, String macaddress) {
		String machineStatus;
		Map<String, String> machines;
		getActiveMachines();
		machines = this.activeMachines.get(tenantid);
		machineStatus = machines.get(macaddress);
		return machineStatus;
	}

	private void killAgent() {
		// TODO Auto-generated method stub
	}

}
