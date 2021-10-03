package com.micro.kafkaFirstLevelProducer.controller;

import com.micro.auth.pojo.Machine;
import com.micro.kafkaFirstLevelProducer.kafka.KafkaProducerService;
import com.micro.kafkaFirstLevelProducer.rbac.TokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@ConditionalOnProperty(prefix = "automation", name = "enabled", havingValue = "true")
@RequestMapping("/automation")
public class AutomationController {

  @Autowired
  @Qualifier("automation-token-manager")
  TokenManagerService tokenManagerService;

  @Autowired
  KafkaProducerService kafkaProducerService;

  @RequestMapping(value="/get/machine/{tenantId}/{macAddress}",method = RequestMethod.GET,consumes= MediaType.APPLICATION_JSON_VALUE)
  Machine getMachine(@PathVariable(name="tenantId") String tenantId,@PathVariable(name="macAddress") String macAddress){
    return tokenManagerService.getMachine(tenantId,macAddress);
  }

  @RequestMapping(value="/get/publicKey",method = RequestMethod.GET)
  String getPublicKey(){
    return tokenManagerService.getPublicKey();
  }


  @RequestMapping(value = "/publish/docker/testKafka", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
  public void testKafka(@RequestBody Map<String,Object> requestBody, @RequestHeader HttpHeaders httpHeaders) {
        kafkaProducerService.send("macaddr",requestBody);
  }

  @RequestMapping(value = "/publish/docker", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> postDockerData(@RequestBody Map<String,Object> requestBody, @RequestHeader HttpHeaders httpHeaders) {
    URI uri = null;
    List<String> token =httpHeaders.get("JWToken");
    List<String> macaddress =httpHeaders.get("macAddress");
    List<String> tenants =httpHeaders.get("tenantId");
    String macaddr=macaddress.get(0);
    String tenantId=tenants.get(0);
    if(tokenManagerService.verify(tenantId,macaddr, token.get(0))) {
      kafkaProducerService.send(macaddr,requestBody);
    }
    return ResponseEntity.created(uri).build();
  }



}
