/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rd.adchallenge.domain.EventFactory;
import com.rd.adchallenge.domain.EventProcessingException;

@Component
@Provider
public class EventProcessingExceptionMapper implements ExceptionMapper<EventProcessingException> {
  
  @Autowired
  private EventFactory eventFactory;

  @Override
  public Response toResponse(EventProcessingException exception) {
    return Response
        .ok()
        .entity(eventFactory.createErrorResult(exception.getErrorCode(), exception.getMessage()))
        .build();
  }
  
}

