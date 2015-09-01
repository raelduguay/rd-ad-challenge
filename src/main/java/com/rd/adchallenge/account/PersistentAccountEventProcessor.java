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
package com.rd.adchallenge.account;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rd.adchallenge.entities.AccountEntity;
import com.rd.adchallenge.entities.AccountEntityRepository;
import com.rd.adchallenge.event.AccountNotFoundException;
import com.rd.adchallenge.event.Event;

/**
 * Perform the event processing against an account, delimited by a DB transaction
 */
@Service
public class PersistentAccountEventProcessor implements AccountEventProcessor {
  
  @Autowired
  private AccountEntityRepository repository;

  @Override
  @Transactional
  public Long onOrder(Event event) {
    AccountEntity newAccount = new AccountEntity();
    AccountEventProcessorDelegate delegate = new AccountEventProcessorDelegate(repository, newAccount);
    return delegate.getEventProcessor().onOrder(event);
  }

  @Override
  @Transactional
  public void onAssign(Event event) {
    AccountEventProcessorDelegate delegate = createDelegateForExistingAccount(event);
    delegate.getEventProcessor().onAssign(event);
  }

  @Override
  @Transactional
  public void onUnassign(Event event) {
    AccountEventProcessorDelegate delegate = createDelegateForExistingAccount(event);
    delegate.getEventProcessor().onUnassign(event);
  }

  @Override
  @Transactional
  public void onChange(Event event) {
    AccountEventProcessorDelegate delegate = createDelegateForExistingAccount(event);
    delegate.getEventProcessor().onChange(event);
  }

  @Override
  @Transactional
  public void onCancel(Event event) {
    AccountEventProcessorDelegate delegate = createDelegateForExistingAccount(event);
    delegate.getEventProcessor().onCancel(event);
  }

  @Override
  @Transactional
  public void onNotice(Event event) {
    AccountEventProcessorDelegate delegate = createDelegateForExistingAccount(event);
    delegate.getEventProcessor().onNotice(event);
  }

  protected AccountEventProcessorDelegate createDelegateForExistingAccount(Event event) {
    AccountEntity account = repository.findOne(Long.parseLong(event.getAccountId()));
    if (account == null)
      throw new AccountNotFoundException(event.getAccountId());
 
    return new AccountEventProcessorDelegate(repository, account);
  }

}

