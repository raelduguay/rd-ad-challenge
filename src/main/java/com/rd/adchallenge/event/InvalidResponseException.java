/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.event;

public class InvalidResponseException extends EventProcessingException {

  private static final long serialVersionUID = 5759130252242683432L;

  public InvalidResponseException(String message) {
    super(EventProcessingResult.ERROR_CODE_INVALID_RESPONSE, message);
  }
  
  public InvalidResponseException(String messageFormat, Object... args) {
    super(EventProcessingResult.ERROR_CODE_INVALID_RESPONSE, messageFormat, args);
  }
  
  public InvalidResponseException(Throwable cause) {
    super(EventProcessingResult.ERROR_CODE_INVALID_RESPONSE, cause);
  }
  
}

