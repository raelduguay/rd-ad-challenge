/* 
 * Author: rael
 * Date  : Aug 28, 2015
 */
package com.rd.adchallenge.ui;

import javax.servlet.annotation.WebServlet;

import org.springframework.context.annotation.Configuration;

import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.server.SpringVaadinServlet;

@Configuration
@EnableVaadin
public class VaadinConfiguration {
  
  @WebServlet(value = { "/ui/*", "/VAADIN/*" }, asyncSupported = true)
  public static class Servlet extends SpringVaadinServlet {
    private static final long serialVersionUID = -5515015488891443037L;
  }
  
}

