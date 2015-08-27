/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.audit;

import java.util.List;

import com.rd.adchallenge.domain.Event;

public interface EventAuditor {

  void eventReceived(Event event);
  
  List<Event> getReceivedEvents();
  
}

