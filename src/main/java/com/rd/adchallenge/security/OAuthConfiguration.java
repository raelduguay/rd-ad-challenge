/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.security;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth.common.signature.SharedConsumerSecretImpl;
import org.springframework.security.oauth.provider.BaseConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetails;

@Configuration
public class OAuthConfiguration {

  @Value("${oauth.consumer-key}")
  private String consumerKey;
  
  @Value("${oauth.consumer-secret}")
  private String consumerSecret;
  
  @Bean
  public OAuthConsumer oauthConsumer() {
    return new DefaultOAuthConsumer(consumerKey, consumerSecret);
  }
  
  @Bean
  public ConsumerDetails consumerDetails() {
    BaseConsumerDetails consumerDetails = new BaseConsumerDetails();
    consumerDetails.setConsumerName("AppDirect");
    consumerDetails.setConsumerKey(consumerKey);
    consumerDetails.setSignatureSecret(new SharedConsumerSecretImpl(consumerSecret));
    return consumerDetails;
  }
  
}

