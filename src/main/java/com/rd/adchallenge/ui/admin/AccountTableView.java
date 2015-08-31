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
 * Date  : Aug 29, 2015
 *
 * ---------------------------------------------------------------------------
 */
package com.rd.adchallenge.ui.admin;

import java.util.Collection;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.rd.adchallenge.domain.Account;
import com.rd.adchallenge.domain.AccountRepository;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;

public class AccountTableView extends AbstractTableAdminView<Account, String> {
  
  private static final long serialVersionUID = -5367092298608296366L;
  
  private final AccountRepository accountRepository;

  public AccountTableView(AccountRepository accountRepository) {
    super(Account.class);
    
    if (accountRepository == null) 
      throw new IllegalArgumentException("accountRepository");
    
    this.accountRepository = accountRepository;
    
    setContent();
  }

  @Override
  protected String getTopLabelCaption() {
    return "Accounts created since last app startup";
  }

  @Override
  protected BeanIdResolver<String, Account> getBeanIdResolver() {
    return b -> b.getId();
  }

  @Override
  protected Object[] getVisibleColumns() {
    return new String[] { "id", "stateName", "editionCode", "commaSeparatedAssignedUserFullNames" };
  }

  @Override
  protected Collection<Account> getCurrentBeans() {
    return Lists.newArrayList(accountRepository.findAll());
  }
  
}

