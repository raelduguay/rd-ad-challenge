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

