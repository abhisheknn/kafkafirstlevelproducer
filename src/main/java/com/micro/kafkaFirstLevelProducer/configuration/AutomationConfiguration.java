package com.micro.kafkaFirstLevelProducer.configuration;

import com.micro.auth.client.TokenManagerServiceClient;
import com.micro.auth.client.TokenManagerServiceClientConfiguration;
import com.micro.auth.pojo.Machine;
import com.micro.kafkaFirstLevelProducer.rbac.TokenManagerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "automation", name = "enabled", havingValue = "true")
public class AutomationConfiguration {

  @Bean(name="automation-token-manager")
  public TokenManagerService getTokenManagerService(){
    TokenManagerService tokenManagerService=new TokenManagerService();
    return tokenManagerService;
  }

  @Bean
  public TokenManagerServiceClient getTokenManagerClient(){
    TokenManagerServiceClient tokenManagerServiceClient=new TokenManagerServiceClient() {
      @Override
      public Machine getMachine(String tenantId, String macAddress) {
        return null;
      }

      @Override
      public Map<String, String> getPublicKey() {
        return null;
      }
    };
    return tokenManagerServiceClient;
  }

}
