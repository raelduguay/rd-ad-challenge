/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.ui.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.rd.adchallenge.entities.AccountEntityRepository;
import com.rd.adchallenge.event.EventAuditor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI(path="/admin")
@Title(AdminUi.TITLE)
public class AdminUi extends UI {
  
  private static final long serialVersionUID = -4758138439938475856L;

  public static final String TITLE = "App Direct Challenge Admin";
  
  @Autowired
  private EventAuditor eventAuditor;
  
  @Autowired
  private AccountEntityRepository accountRepository;
  
  @Override
  protected void init(VaadinRequest request) {
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    layout.setSpacing(true);
    
    Label title = new Label(TITLE);
    title.setStyleName("h1");
    layout.addComponent(title);
    
    TabSheet tabSheet = new TabSheet();
    tabSheet.setSizeFull();
    layout.addComponent(tabSheet);
    
    EventLogTableView auditView = new EventLogTableView(eventAuditor);
    auditView.setCaption("Events Audit");
    tabSheet.addComponent(auditView);

    AccountTableView accountsView = new AccountTableView(accountRepository);
    accountsView.setCaption("Accounts");
    tabSheet.addComponent(accountsView);
    
    setContent(layout);
  }

}

