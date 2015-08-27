/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.audit;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rd.adchallenge.domain.Event;

@Component
public class InMemoryEventAuditor implements EventAuditor {
  
  private final List<Event> events = new LinkedList<>();

  @Override
  public void eventReceived(Event event) {
    if (event == null) throw new IllegalArgumentException("event");
    synchronized(events) {
      events.add(event);
    }
  }

  /**
   * Returns a shallow copy of events, mutations on returned
   * list has no effect. Not efficient in real-world scenario!
   */
  @Override
  public List<Event> getReceivedEvents() {
    synchronized(events) {
      return new LinkedList<>(events);
    }
  }

}

