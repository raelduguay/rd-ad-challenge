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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class AccountEntity {
  
  @Id
  @GeneratedValue
  private Long id;
  
  @Version
  private Long version;

  @Column
  private String stateName;
  
  @Column
  private String editionCode;
  
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<User> assignedUsers = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public String getStateName() {
    return stateName;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public String getEditionCode() {
    return editionCode;
  }

  public void setEditionCode(String editionCode) {
    this.editionCode = editionCode;
  }

  public Set<User> getAssignedUsers() {
    return assignedUsers;
  }

  public void setAssignedUsers(Set<User> assignedUsers) {
    this.assignedUsers = assignedUsers;
  }
  
  public String getCommaSeparatedAssignedUserFullNames() {
    return assignedUsers
        .stream()
        .map(u -> u.getFullName())
        .collect(Collectors.joining(","));
  }
  
}

