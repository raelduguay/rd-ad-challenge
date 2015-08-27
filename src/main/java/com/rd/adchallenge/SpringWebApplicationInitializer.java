/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringWebApplicationInitializer extends AbstractContextLoaderInitializer {

  private static final String CONFIG_LOCATION = "com.rd.adchallenge";

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // Disable Jersey's SpringWebApplicationInitializer
    servletContext.setInitParameter("contextConfigLocation", CONFIG_LOCATION);
    super.onStartup(servletContext);
  }

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
    rootCtx.setConfigLocation(CONFIG_LOCATION);
    return rootCtx;
  }

}

