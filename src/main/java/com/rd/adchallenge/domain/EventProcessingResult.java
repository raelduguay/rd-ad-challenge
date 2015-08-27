/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventProcessingResult {
  
  public static final String ERROR_CODE_INVALID_RESPONSE = "INVALID_RESPONSE";
  public static final String ERROR_CODE_USER_NOT_FOUND = "USER_NOT_FOUND";
  public static final String ERROR_CODE_ACCOUNT_NOT_FOUND = "ACCOUNT_NOT_FOUND";

  @XmlElement
  private boolean success;

  @XmlElement
  private String errorCode;

  @XmlElement
  private String message;

  @XmlElement
  private String accountIdentifier;

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAccountIdentifier() {
    return accountIdentifier;
  }

  public void setAccountIdentifier(String accountIdentifier) {
    this.accountIdentifier = accountIdentifier;
  }
}
