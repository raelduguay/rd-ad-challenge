/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.event;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rd.adchallenge.entities.EventEntity;
import com.rd.adchallenge.entities.EventEntityRepository;

/**
 * Implements {@link EventAuditor} backed by a JPA repository
 */
@Service
public class PersistentEventAuditor implements EventAuditor {
  
  @Autowired
  private EventEntityRepository repository;
  
  @Autowired
  private EventFactory eventFactory;
  
  @Override
  public void eventReceived(Event event) {
    if (event == null) 
      throw new IllegalArgumentException("event");
    
    EventEntity eventEntity = new EventEntity();
    eventEntity.setRawXml(event.getRawXml());
    repository.save(eventEntity);
  }

  @Override
  public List<Event> getReceivedEvents() {
    return StreamSupport
      .stream(repository.findAll().spliterator(), false)
      .map(en -> eventFactory.createEvent(en.getRawXml()))
      .collect(Collectors.toList());
  }

}

