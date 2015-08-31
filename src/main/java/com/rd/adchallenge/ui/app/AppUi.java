/* 
 * Author: rael
 * Date  : Aug 28, 2015
 */
package com.rd.adchallenge.ui.app;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI(path="/app")
@Title(AppUi.TITLE)
public class AppUi extends UI {

  private static final long serialVersionUID = 8477406446716066381L;
  
  public static final String TITLE = "App Direct Challenge App!";

  @Override
  protected void init(VaadinRequest request) {
    VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    
    Label title = new Label(TITLE);
    title.setStyleName("h1");
    layout.addComponent(title);
    
    setContent(layout);
  }
  
}

