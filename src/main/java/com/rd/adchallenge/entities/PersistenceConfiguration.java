/* 
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Nuance Communications Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Nuance Communications Inc. The program(s) may be used and/or copied
 * only with the written permission from Nuance Communications Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 *
 * Author: rael
 * Date  : Aug 31, 2015
 *
 * ---------------------------------------------------------------------------
 */
package com.rd.adchallenge.entities;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

public class PersistenceConfiguration {

  @Configuration
  public static class DataSourceConfiguration {
    @Bean
    public DataSource devDataSource() {
      return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .build();
    }
  }
  
  @Configuration
  @EnableJpaRepositories("com.rd.adchallenge.entities")
  public static class JpaConfiguration {
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      
      factory.setDataSource(dataSource);
      factory.setPackagesToScan("com.rd.adchallenge.entities");
      factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
      
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      vendorAdapter.setGenerateDdl(true);
      vendorAdapter.setShowSql(true);
      factory.setJpaVendorAdapter(vendorAdapter);
      
      Properties jpaProperties = new Properties();
      jpaProperties.put("hibernate.hbm2ddl.auto", "update");
      factory.setJpaProperties(jpaProperties);
      
      factory.afterPropertiesSet();
      
      return factory;
    }
  }
  
  @Configuration
  @EnableTransactionManagement
  public static class TransactionConfiguration {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Bean
    public PlatformTransactionManager transactionManager() {
      return new JpaTransactionManager(entityManagerFactory);
    }
  }
  
}

