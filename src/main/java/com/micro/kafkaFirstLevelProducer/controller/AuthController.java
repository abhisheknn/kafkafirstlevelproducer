package com.micro.kafkaFirstLevelProducer.controller;

import com.micro.auth.pojo.Machine;
import com.micro.kafkaFirstLevelProducer.constants.Constants;
import com.micro.kafkaFirstLevelProducer.kafka.KafkaProducerService;
import com.micro.kafkaFirstLevelProducer.rbac.TokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  TokenManagerService tokenManagerService;

  @RequestMapping(value="/publicKey",method = RequestMethod.GET)
  String getPublicKey(){
    return tokenManagerService.getPublicKey();
  }

  @RequestMapping(value="/register",method = RequestMethod.POST)
  String register(@RequestBody Map<String,String> request){
    return tokenManagerService.register(request.get(Constants.TENANT_ID),request.get(Constants.MACADDRESS));
  }

}
