/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.rest;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rd.adchallenge.account.AccountEventProcessor;
import com.rd.adchallenge.event.Event;
import com.rd.adchallenge.event.EventAuditor;
import com.rd.adchallenge.event.EventFactory;

public abstract class AbstractAppDirectApiResource {
  
  private static final Log LOGGER = LogFactory.getLog(AbstractAppDirectApiResource.class);

  @Autowired
  protected AccountEventProcessor accountProcessor;
  
  @Autowired
  protected EventFactory eventFactory;
  
  @Autowired
  private EventAuditor eventAuditor;

  @Autowired
  private OAuthConsumer oauthConsumer;
  
  @Autowired
  private Client client;

  protected Event fetchEventAndAudit(String eventUrl) {
    if (StringUtils.isBlank(eventUrl)) throw new IllegalArgumentException("eventUrl");
    
    String eventRawXml = doFetchEventContent(eventUrl);
    Event event = eventFactory.createEvent(eventRawXml);
  
    eventAuditor.eventReceived(event);
    
    return event;
  }

  private String doFetchEventContent(String eventUrl) {
    
    try {
      String signedUrl = oauthConsumer.sign(eventUrl);
      LOGGER.debug("Fecthing event content on signed event url=[" + signedUrl + "]");
    
      WebTarget target = client.target(signedUrl);
      String eventRawXml = target.request(MediaType.APPLICATION_XML_TYPE).get(String.class);
      
      LOGGER.debug("Event content: [" + eventRawXml + "]");
      
      return eventRawXml;
    } catch (OAuthException e) {
      throw new InternalServerErrorException(e);
    }
  }
  
}

