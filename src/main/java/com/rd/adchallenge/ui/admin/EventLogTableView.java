/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.ui.admin;

import java.util.Collection;

import com.rd.adchallenge.event.EventAuditor;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;

public class EventLogTableView extends AbstractTableAdminView<com.rd.adchallenge.event.Event, Long> {
  
  private static final long serialVersionUID = -3590687950457104149L;
  
  private final EventAuditor eventAuditor;

  public EventLogTableView(EventAuditor eventAuditor) {
    super(com.rd.adchallenge.event.Event.class);
    
    if (eventAuditor == null) 
      throw new IllegalArgumentException("eventAuditor");
    
    this.eventAuditor = eventAuditor;
    
    setContent();
  }
  
  @Override
  protected String getTopLabelCaption() {
    return "Events received since server started";
  }

  @Override
  protected BeanIdResolver<Long, com.rd.adchallenge.event.Event> getBeanIdResolver() {
    return b -> b.getId();
  }

  @Override
  protected Object[] getVisibleColumns() {
    return new String[] { "id", "timestamp", "eventType", "creatorEmail", "editionCode", "accountId", "accountStatus", "noticeType" };
  }

  @Override
  protected Collection<com.rd.adchallenge.event.Event> getCurrentBeans() {
    return eventAuditor.getReceivedEvents();
  }

}

