package com.micro.kafkaFirstLevelProducer.kafka.publish;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.kafkaFirstLevelProducer.kafka.KafkaProducerService;

@RestController
@RequestMapping(value = "/publish")
public class Publish {

	@Autowired
	KafkaProducerService kafkaProducer;

	@RequestMapping(value = "/docker",params = {"hostname"}, method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postDockerData(@RequestParam(value ="hostname", required=false) String hostname, @RequestBody Map<String,Object> requestBody) {
		URI uri = null;
		kafkaProducer.send(hostname,requestBody);
		return ResponseEntity.created(uri).build();
	}
}
