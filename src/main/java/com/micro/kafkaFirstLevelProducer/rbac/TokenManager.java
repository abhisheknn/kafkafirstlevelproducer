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

	@Autowired
	private MachineStatus machineStatus;
	
	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Type mapType = new TypeToken<Map<String, String>>() {
	}.getType();
	Set<String> tenants = new HashSet<>();

	@Scheduled(fixedRate = 10000)
	public void getPublicKey() {
		Response publicKey= restClient.doGet(Constants.TOKENMANAGER_GETPUBLICKEY, Constants.commonHeaders);
		 this.publicKey =publicKey.getEntity();
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
		String status="inactive";
		Map<String, String> machines = machineStatus.getActiveMachines().get(tenantid);
		if (null != machines) {
			status = machines.get(macaddress);
		}
		return status;
	}

	private void killAgent() {
		// TODO Auto-generated method stub
	}

}
