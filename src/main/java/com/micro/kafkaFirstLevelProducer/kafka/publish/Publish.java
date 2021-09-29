package com.micro.kafkaFirstLevelProducer.kafka.publish;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.micro.kafkaFirstLevelProducer.rbac.TokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.micro.kafkaFirstLevelProducer.kafka.KafkaProducerService;


@RestController
@RequestMapping(value = "/publish")
public class Publish {

	@Autowired
	KafkaProducerService kafkaProducer;

	//@Autowired
  TokenManagerService tokenManager;

	// send every thing in headers
	@RequestMapping(value = "/docker", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postDockerData(@RequestBody Map<String,Object> requestBody,@RequestHeader HttpHeaders httpHeaders) {
		URI uri = null;
		List<String> token =httpHeaders.get("JWToken");
		List<String> macaddress =httpHeaders.get("macAddress");
		List<String> tenants =httpHeaders.get("tenantId");
		String macaddr=macaddress.get(0);
		String tenantId=tenants.get(0);
		if(tokenManager.verify(tenantId,macaddr, token.get(0))) {
			kafkaProducer.send(macaddr,requestBody);
		}
		return ResponseEntity.created(uri).build();
	}
}
