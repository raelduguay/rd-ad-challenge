/* 
 * ---------------------------------------------------------------------------
 *
 * COPYRIGHT (c) 2015 Nuance Communications Inc. All Rights Reserved.
 *
 * The copyright to the computer program(s) herein is the property of
 * Nuance Communications Inc. The program(s) may be used and/or copied
 * only with the written permission from Nuance Communications Inc.
 * or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program(s) have been supplied.
 *
 * Author: rael
 * Date  : Aug 28, 2015
 *
 * ---------------------------------------------------------------------------
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
  // TODO RD HERE Final refactorings (naming, consistency)
  // TODO RD HERE Publish on Heroku or similar...

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

