/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.ui;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.rd.adchallenge.audit.EventAuditor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI(path="/admin")
@Title("App Direct Challenge")
public class AdChallengeUi extends UI {
  
  // TODO RD HERE Account table view, put in tab

  private static final long serialVersionUID = -4758138439938475856L;
  
  @WebServlet(value = { "/ui/*", "/VAADIN/*" }, asyncSupported = true)
  public static class Servlet extends SpringVaadinServlet {
    private static final long serialVersionUID = -5515015488891443037L;
  }
  
  @Configuration
  @EnableVaadin
  public static class MyConfiguration {
  }  
  
  @Autowired
  private EventAuditor eventAuditor;
  
  @Override
  protected void init(VaadinRequest request) {
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    
    Label title = new Label("App Direct Challenge");
    title.setStyleName("h1");
    layout.addComponent(title);
    
    AuditView auditView = new AuditView(eventAuditor);
    auditView.setSizeFull();
    layout.addComponent(auditView);
    
    setContent(layout);
  }

}

