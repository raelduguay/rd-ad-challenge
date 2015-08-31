/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Tolerant-reader-style of encapsulation of an event fetched in response of a notification received
 * from App Direct. Relies on XPath to get various elements from the underlying event XML document.
 */
public class Event {
  
  private static final AtomicLong idSequence = new AtomicLong();

  private final long id = idSequence.incrementAndGet();
  private final Date timestamp = new Date();
  
  private final String rawXml;
  private final Document document;
  private final XPath xpath;

  public Event(String rawXml, Document document, XPath xpath) {
    super();

    if (StringUtils.isBlank(rawXml)) 
      throw new IllegalArgumentException("rawXml");
    if (document == null) 
      throw new IllegalArgumentException("document");
    if (xpath == null) 
      throw new IllegalArgumentException("xpath");

    this.rawXml = rawXml;
    this.document = document;
    this.xpath = xpath;
  }

  public long getId() {
    return id;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getEventType() {
    return evaluateXpathAsString("/event/type");
  }

  public String getRawXml() {
    return rawXml;
  }

  public String getCreatorEmail() {
    return evaluateXpathAsString("/event/creator/email");
  }
  
  public User getCreatorUser() {
    Node creatorNode = evaluateXpathAsNode("/event/creator");
    return getUser(creatorNode);
  }
  
  public String getEditionCode() {
    return evaluateXpathAsString("/event/payload/order/editionCode");
  }
  
  public String getAccountId() {
    return evaluateXpathAsString("/event/payload/account/accountIdentifier");
  }
  
  public String getAccountStatus() {
    return evaluateXpathAsString("/event/payload/account/status");
  }
  
  public String getNoticeType() {
    return evaluateXpathAsString("/event/payload/notice/type");
  }
  
  public Optional<User> getPayloadUser() {
    Node userNode = evaluateXpathAsNode("/event/payload/user");
    if (userNode == null) {
      return Optional.empty();
    }

    return Optional.of(getUser(userNode));
  }
  
  protected User getUser(Node userNode) {
    String openId = evaluateXpathAsString("openId", userNode);
    String firstName = evaluateXpathAsString("firstName", userNode);
    String lastName = evaluateXpathAsString("lastName", userNode);
    String email = evaluateXpathAsString("email", userNode);
    
    return new User(openId, firstName + " " + lastName, email);
  }

  private String evaluateXpathAsString(String expression) {
    return (String)evaluateXpath(expression, document, XPathConstants.STRING);
  }
  
  private String evaluateXpathAsString(String expression, Node startingNode) {
    return (String)evaluateXpath(expression, startingNode, XPathConstants.STRING);
  }
  
  private Node evaluateXpathAsNode(String expression) {
    return (Node)evaluateXpath(expression, document, XPathConstants.NODE);
  }
  
  private Object evaluateXpath(String expression, Node startingNode, QName returnType) {
    try {
      return xpath.evaluate(expression, startingNode, returnType);
    } catch (XPathExpressionException e) {
      throw new IllegalArgumentException("Invalid xpath expression: " + expression, e);
    }
  }
  
}

