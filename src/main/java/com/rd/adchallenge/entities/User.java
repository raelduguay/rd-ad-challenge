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

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class User {
  
  @Column(nullable = false)
  private String openId;
  
  @Column(nullable = false)
  private String fullName;
  
  @Column(nullable = false)
  private String email;

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
      .append(openId)
      .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null) return false;
    if (!(obj instanceof User)) return false;
    User userObj = (User)obj;
    return new EqualsBuilder()
      .append(openId, userObj.openId)
      .isEquals();
  }
  
}

