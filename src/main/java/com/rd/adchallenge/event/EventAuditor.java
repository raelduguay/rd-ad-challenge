/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.event;

import java.util.List;

/**
 * Simple interface to record and retrieve events received through App Direct
 */
public interface EventAuditor {

  void eventReceived(Event event);
  
  List<Event> getReceivedEvents();
  
}

