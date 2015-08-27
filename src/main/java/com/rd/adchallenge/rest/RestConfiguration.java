/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.rest;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {
  
  @Configuration
  @ApplicationPath("rest")
  public static class ServerConfiguration extends ResourceConfig implements InitializingBean {
    
    @Autowired
    private LoggingFilter loggingFilter;

    @Override
    public void afterPropertiesSet() throws Exception {
      packages("com.rd.adchallenge.rest");
      register(loggingFilter);
    }
    
  }

  @Configuration
  public static class ClientConfiguration {
    
    @Autowired
    private LoggingFilter loggingFilter;
    
    @Bean
    public Client client() {
      return ClientBuilder.newClient().register(loggingFilter);    
    }
  }

  @Bean
  public LoggingFilter loggingFilter() {
    return new LoggingFilter(Logger.getLogger(LoggingFilter.class.getName()), true);
  }
}

