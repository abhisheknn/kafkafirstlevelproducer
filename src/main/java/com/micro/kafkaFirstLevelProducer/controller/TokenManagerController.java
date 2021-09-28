package com.micro.kafkaFirstLevelProducer.controller;

import com.micro.auth.pojo.Machine;
import com.micro.kafkaFirstLevelProducer.rbac.TokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class TokenManagerController {

  @Autowired
  TokenManagerService tokenManagerService;

  @RequestMapping(value="/get/machine/{tenantId}/{macAddress}",method = RequestMethod.GET,consumes= MediaType.APPLICATION_JSON_VALUE)
  Machine getMachine(@PathVariable(name="tenantId") String tenantId,@PathVariable(name="macAddress") String macAddress){
    return tokenManagerService.getMachine(tenantId,macAddress);
  }

  @RequestMapping(value="/get/publicKey",method = RequestMethod.GET)
  String getPublicKey(){
    return tokenManagerService.getPublicKeyFromController();
  }



}
