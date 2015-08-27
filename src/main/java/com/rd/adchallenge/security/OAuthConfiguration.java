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
  
}

