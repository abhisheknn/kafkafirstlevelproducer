package com.micro.kafkaFirstLevelProducer.configuration;

import com.micro.auth.client.TokenManagerServiceClient;
import com.micro.auth.client.TokenManagerServiceClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenManagerServiceConfiguration {
  @Autowired
  DiscoveryClient discoveryClient;
  @Bean
  public TokenManagerServiceClient getTokenManagerClient(){
    TokenManagerServiceClientConfiguration tokenManagerServiceConfiguration=new TokenManagerServiceClientConfiguration(discoveryClient);
    return tokenManagerServiceConfiguration.getTokenManagerServiceClient();
  }
}
