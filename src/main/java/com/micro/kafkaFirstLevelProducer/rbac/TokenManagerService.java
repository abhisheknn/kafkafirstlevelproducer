package com.micro.kafkaFirstLevelProducer.rbac;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.micro.auth.client.TokenManagerServiceClient;
import com.micro.auth.pojo.Machine;
import com.micro.kafkaFirstLevelProducer.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.micro.kafkaFirstLevelProducer.client.RestClient;

import io.jsonwebtoken.Claims;

@Component
@ConditionalOnProperty(prefix = "tokenManager", name = "enabled", havingValue = "true")
public class TokenManagerService {

  @Value(value = "${schedule.getPublicKey.enabled}")
  private boolean getPublicKeyEnabled;

	@Autowired
	private RestClient restClient;

	@Autowired
	private JWT jwt;

	private String publicKey;

	@Autowired
	 private MachineStatusService machineStatusService;

	@Autowired
  private TokenManagerServiceClient tokenManagerServiceClient;

	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	Type mapType = new TypeToken<Map<String, String>>() {
	}.getType();
	Set<String> tenants = new HashSet<>();

	@Scheduled(fixedRate = 10000)
	public void getPublicKeySchedule() {
	  if(getPublicKeyEnabled) {
      Map<String, String> publicKey = tokenManagerServiceClient.getPublicKey();
      this.publicKey = (String) publicKey.get("publicKey");
    }
	}

  public String getPublicKey() {
    Map<String,String> publicKey=tokenManagerServiceClient.getPublicKey();
    this.publicKey =(String) publicKey.get("publicKey");
    return this.publicKey;
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
		Map<String, String> machines = machineStatusService.getActiveMachines().get(tenantid);
		if (null != machines) {
			status = machines.get(macaddress);
		}
		return status;
	}

	private void killAgent() {
		// TODO Auto-generated method stub
	}

	public Machine getMachine(String tenantId,String macAddress){
    return tokenManagerServiceClient.getMachine(tenantId,macAddress);
  }

  public String register(String tenantId, String macAddress){
    Machine machine=tokenManagerServiceClient.getMachine(tenantId,macAddress);
    return machine.getJWToken();
  }


}
