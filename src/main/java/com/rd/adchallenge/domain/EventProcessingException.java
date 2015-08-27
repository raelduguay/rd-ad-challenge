/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

public class EventProcessingException extends RuntimeException {

  private static final long serialVersionUID = 3608384110340518851L;
  
  private final String errorCode;

  public EventProcessingException(String errorCode) {
    super();
    this.errorCode = errorCode;
  }

  public EventProcessingException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }
  
  public EventProcessingException(String errorCode, String messageFormat, Object... args) {
    super(String.format(messageFormat, args));
    this.errorCode = errorCode;
  }
  
  public EventProcessingException(String errorCode, Throwable cause) {
    super(cause);
    this.errorCode = errorCode;
  }
  
  public String getErrorCode() {
    return errorCode;
  }
  
}

