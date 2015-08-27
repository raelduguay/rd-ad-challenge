/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

/**
 * Spring-data repository style of repo (could be easily migrated as such if persistency is implemented) 
 */
public interface AccountRepository {
  
  /** 
   * New instance in initial state - not persisted
   */
  Account createAccount();
  
  /**
   * Persist new account, throws if account with same id exists
   */
  Account save(Account account);

  Iterable<Account> findAll();
  
  /**
   * @return <code>null</code> if not found
   */
  Account findById(String accountId);
}

