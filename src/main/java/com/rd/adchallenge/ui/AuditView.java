/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.ui;

import com.rd.adchallenge.audit.EventAuditor;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AuditView extends CustomComponent {
  
  private static final long serialVersionUID = -3590687950457104149L;
  
  private final EventAuditor eventAuditor;
  private final BeanContainer<Long, com.rd.adchallenge.domain.Event> eventContainer = new BeanContainer<>(com.rd.adchallenge.domain.Event.class);

  public AuditView(EventAuditor eventAuditor) {
    super();
    
    if (eventAuditor == null) 
      throw new IllegalArgumentException("eventAuditor");
    
    this.eventAuditor = eventAuditor;
    
    setContent();
  }
  
  protected void setContent() {
    VerticalLayout layout = new VerticalLayout();
    layout.setSpacing(true);
    
    eventContainer.setBeanIdResolver(b -> b.getId());
    updateContainerItems();
    
    Table eventTable = new Table("Event Log", eventContainer);
    eventTable.setVisibleColumns("id", "timestamp", "eventType", "creatorEmail", "editionCode", "accountId", "accountStatus", "noticeType");
    eventTable.setWidth("100%");
    eventTable.setPageLength(10);
    eventTable.setSortAscending(false);
    layout.addComponent(eventTable);
    
    Button refresh = new Button("refesh");
    refresh.addClickListener(e -> updateContainerItems());
    layout.addComponent(refresh);
    
    setCompositionRoot(layout);
  }
  
  protected void updateContainerItems() {
    // Barebone UI - not suitable (scalable) for real product UI
    eventContainer.removeAllItems();
    eventContainer.addAll(eventAuditor.getReceivedEvents());
    eventContainer.sort(new Object[] { "id" }, new boolean[] { false });
  }

}

