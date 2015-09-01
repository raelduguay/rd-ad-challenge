/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.rd.adchallenge.event.Event;
import com.rd.adchallenge.event.EventProcessingResult;

@Path("/assignation")
@Component
public class UserAssignmentResource extends AbstractAppDirectApiResource {
  
  private final Log LOGGER = LogFactory.getLog(UserAssignmentResource.class);

  @GET
  @Path("/assign")
  public EventProcessingResult assign(
      @QueryParam("eventUrl") String eventUrl) {
    
    LOGGER.info("assign: eventUrl=[" + eventUrl +"]");
    
    Event event = fetchEventAndAudit(eventUrl);
    accountProcessor.onAssign(event);
    
    return eventFactory.createSuccessfulResult();
  }
  
  @GET
  @Path("/unassign")
  public EventProcessingResult unassign(      
      @QueryParam("eventUrl") String eventUrl) {
    
    LOGGER.info("unassign: eventUrl=[" + eventUrl +"]");
    
    Event event = fetchEventAndAudit(eventUrl);
    accountProcessor.onUnassign(event);

    return eventFactory.createSuccessfulResult();
  }
  
}

