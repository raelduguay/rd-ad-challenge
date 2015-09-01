/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rd.adchallenge.event.Event;
import com.rd.adchallenge.event.EventProcessingResult;

@Path("/subscription")
@Component
public class SubscriptionResource extends AbstractAppDirectApiResource {
  
  private static final Log LOGGER = LogFactory.getLog(SubscriptionResource.class);
  
  @GET
  @Path("/create")
  @Produces(MediaType.APPLICATION_XML)
  public EventProcessingResult create(
      @QueryParam("eventUrl") String eventUrl) {
    
    LOGGER.info("create: eventUrl=[" + eventUrl +"]");
    
    Event event = fetchEventAndAudit(eventUrl);
    Long createdAccountId = accountProcessor.onOrder(event);
    
    return eventFactory.createSuccessfulResult("" + createdAccountId);
  }

  @GET
  @Path("/change")
  @Produces(MediaType.APPLICATION_XML)
  public EventProcessingResult change(
      @QueryParam("eventUrl") String eventUrl) {
    
    LOGGER.info("change: eventUrl=[" + eventUrl + "]");

    Event event = fetchEventAndAudit(eventUrl);
    accountProcessor.onChange(event);
    
    return eventFactory.createSuccessfulResult();
  }
  
  @GET
  @Path("/cancel")
  @Produces(MediaType.APPLICATION_XML)
  public EventProcessingResult cancel(
      @QueryParam("eventUrl") String eventUrl) {
    
    LOGGER.info("cancel: eventUrl=[" + eventUrl + "]");

    Event event = fetchEventAndAudit(eventUrl);
    accountProcessor.onCancel(event);
    
    return eventFactory.createSuccessfulResult();
  }

  @GET
  @Path("/notice")
  @Produces(MediaType.APPLICATION_XML)
  public EventProcessingResult notice(
      @QueryParam("eventUrl") String eventUrl) {
    
    LOGGER.info("notice: eventUrl=[" + eventUrl + "]");

    Event event = fetchEventAndAudit(eventUrl);
    accountProcessor.onNotice(event);
    
    return eventFactory.createSuccessfulResult();
  }
    
}

