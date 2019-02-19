package com.micro.kafkaFirstLevelProducer.kafka.publish;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.kafkaFirstLevelProducer.kafka.KafkaProducerService;
import com.micro.kafkaFirstLevelProducer.kafka.constants.KafkaConstants;

@RestController
@RequestMapping(value = "/publish")
public class Publish {

	@Autowired
	KafkaProducerService kafkaProducer;
	
	@RequestMapping(value = "/docker",params = {"key"}, method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postDockerData(@RequestParam(value ="key", required=false) String key, @RequestBody Map<String,Object> requestBody,@RequestHeader HttpHeaders httpHeaders) {
		URI uri = null;
		kafkaProducer.send(key,requestBody);
		return ResponseEntity.created(uri).build();
	}
}
