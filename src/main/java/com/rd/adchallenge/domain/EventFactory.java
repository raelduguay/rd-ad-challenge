/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Component
public class EventFactory {
  
  @Autowired
  private DocumentBuilder documentBuilder;
  
  @Autowired
  private XPath xpath;

  public Event createEvent(String rawXml) {
    try (Reader reader = new StringReader(rawXml)) {
      Document document = documentBuilder.parse(new InputSource(reader));
      return new Event(rawXml, document, xpath);
    } catch (IOException | SAXException e) {
      throw new InvalidResponseException(e);
    }
  }
  
  public EventProcessingResult createSuccessfulResult() {
    return createSuccessfulResult(null);
  }
  
  public EventProcessingResult createSuccessfulResult(String accountId) {
    EventProcessingResult result = new EventProcessingResult();
    result.setSuccess(true);
    result.setMessage("success");
    result.setAccountIdentifier(accountId);
    return result;
  }
  
  public EventProcessingResult createErrorResult(String errorCode, String message) {
    EventProcessingResult result = new EventProcessingResult();
    result.setSuccess(false);
    result.setErrorCode(errorCode);
    result.setMessage(message);
    return result;
  }
  
}

