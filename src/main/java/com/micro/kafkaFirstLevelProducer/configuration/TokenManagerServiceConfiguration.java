package com.micro.kafkaFirstLevelProducer.configuration;

import com.micro.auth.client.TokenManagerServiceClient;
import com.micro.auth.client.TokenManagerServiceClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "tokenManager", name = "enabled", havingValue = "true")
public class TokenManagerServiceConfiguration {
  @Autowired
  DiscoveryClient discoveryClient;
  @Bean
  public TokenManagerServiceClient getTokenManagerClient(){
    TokenManagerServiceClientConfiguration tokenManagerServiceConfiguration=new TokenManagerServiceClientConfiguration(discoveryClient);
    return tokenManagerServiceConfiguration.getTokenManagerServiceClient();
  }
}
