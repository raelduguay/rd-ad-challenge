/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser("admin").password("admin").roles("ADMIN");
  }

  /**
   * Add a simple filter on a spring security filter chain that does only signature verification, it
   * doesn't perform any formal Spring Security authentication per se (although it could be).
   * Applies only for AppDirect Subscription API. 
   */
  @Configuration
  @Order(0)
  public static class SubscriptionApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private OAuthSignatureVerifierFilter oauthSignatureVerifierFilter; 
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .requestMatchers().antMatchers("/rest/subscription/**", "/rest/assignation/**")
          .and()
        .addFilterAfter(oauthSignatureVerifierFilter, BasicAuthenticationFilter.class);
    }
  }
  
  /**
   * Form authentication for "admin" UI
   */
  @Configuration
  @Order(1)
  public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers("/ui/**").hasRole("ADMIN")
          .and()
        .formLogin()
          .and()
        .csrf().disable();
    }
  }
  
}

