/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.event;

public class AccountNotFoundException extends EventProcessingException {

  private static final long serialVersionUID = 4699529146294149564L;
  
  private static final String MSG_FMT = "Account with id [%s] not found";
  
  public AccountNotFoundException(String accountId) {
    super(EventProcessingResult.ERROR_CODE_ACCOUNT_NOT_FOUND, MSG_FMT, accountId);
  }
}

