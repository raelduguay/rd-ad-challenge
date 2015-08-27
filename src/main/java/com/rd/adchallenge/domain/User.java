/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * For challenge code, contains just key information
 */
public class User {
  
  /**
   * Acts as unique reference
   */
  private final String openId;

  private final String fullName;
  
  private final String email;

  public User(String openId, String fullName, String email) {
    super();
    
    if (StringUtils.isBlank(openId)) 
      throw new IllegalArgumentException("openId");
    if (StringUtils.isBlank(fullName))
      throw new IllegalArgumentException("fullName");
    if (StringUtils.isBlank(email))
      throw new IllegalArgumentException("email");
    
    this.openId = openId;
    this.fullName = fullName;
    this.email = email;
  }

  public String getOpenId() {
    return openId;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

}

