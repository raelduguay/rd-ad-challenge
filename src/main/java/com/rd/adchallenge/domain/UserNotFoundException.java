/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

public class UserNotFoundException extends EventProcessingException {
  
  private static final long serialVersionUID = 3703337373565255563L;
  
  private static final String MSG_FMT = "User [%s] not assigned to account [%s]";

  public UserNotFoundException(User user, Account account) {
    super(EventProcessingResult.ERROR_CODE_USER_NOT_FOUND, MSG_FMT, user.getOpenId(), account.getId());
  }
  
}

