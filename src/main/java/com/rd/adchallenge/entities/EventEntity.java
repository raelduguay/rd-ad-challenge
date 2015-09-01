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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EventEntity {

  @Id
  @GeneratedValue
  private Long id;
  
  @Column(nullable = false, length=4096)
  private String rawXml;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRawXml() {
    return rawXml;
  }

  public void setRawXml(String rawXml) {
    this.rawXml = rawXml;
  }
  
}

