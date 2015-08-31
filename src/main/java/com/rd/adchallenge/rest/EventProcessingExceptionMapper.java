/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rd.adchallenge.domain.EventFactory;
import com.rd.adchallenge.domain.EventProcessingException;
import com.rd.adchallenge.domain.EventProcessingResult;

@Component
@Provider
public class EventProcessingExceptionMapper implements ExceptionMapper<EventProcessingException> {
  
  @Autowired
  private EventFactory eventFactory;
  
  @Value("${rest.appdirect.swallow-exceptions}")
  private boolean swallowExceptions;

  @Override
  public Response toResponse(EventProcessingException exception) {
    EventProcessingResult result =
        swallowExceptions ? 
            eventFactory.createSuccessfulResult() :
            eventFactory.createErrorResult(exception.getErrorCode(), exception.getMessage());
    
    return Response
        .ok()
        .entity(result)
        .build();
  }
  
}

