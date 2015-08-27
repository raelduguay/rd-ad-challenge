/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class InMemoryAccountRepository implements AccountRepository {
  
  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public Account createAccount() {
    return new Account();
  }
  
  @Override
  public Account save(Account account) {
    if (accounts.putIfAbsent(account.getId(), account) != null) {
      throw new IllegalStateException(String.format("Account [%s] already exists", account.getId()));
    }
    
    return account;
  }

  @Override
  public Iterable<Account> findAll() {
    return new HashSet<>(accounts.values());
  }

  @Override
  public Account findById(String accountId) {
    return accounts.get(accountId);
  }

}

