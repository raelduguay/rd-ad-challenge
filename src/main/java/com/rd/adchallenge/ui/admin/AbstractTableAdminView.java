/* 
 * Author: rael
 * Date  : Aug 29, 2015
 */
package com.rd.adchallenge.ui.admin;

import java.util.Collection;

import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractTableAdminView<BEANTYPE, IDTYPE> extends CustomComponent {

  private static final long serialVersionUID = -3376463944809600607L;
  
  private final BeanContainer<IDTYPE, BEANTYPE> eventContainer;

  protected AbstractTableAdminView(Class<BEANTYPE> beanType) {
    super();
    eventContainer = new BeanContainer<>(beanType);
  }
  
  protected void setContent() {
    VerticalLayout layout = new VerticalLayout();
    layout.setSpacing(true);
    
    Label topLabel = new Label(getTopLabelCaption());
    layout.addComponent(topLabel);
    
    eventContainer.setBeanIdResolver(getBeanIdResolver());
    updateContainerItems();
    
    Table eventTable = new Table();
    eventTable.setContainerDataSource(eventContainer);
    
    Object[] visibleColumns = getVisibleColumns();
    eventTable.setVisibleColumns(visibleColumns);
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
    eventContainer.addAll(getCurrentBeans());
    eventContainer.sort(new Object[] { "id" }, new boolean[] { false });
  }
  
  protected abstract String getTopLabelCaption();
  protected abstract BeanIdResolver<IDTYPE, BEANTYPE> getBeanIdResolver();
  protected abstract Object[] getVisibleColumns();
  protected abstract Collection<BEANTYPE> getCurrentBeans();
  
}

