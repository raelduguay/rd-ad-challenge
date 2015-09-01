/* 
 * Author: rael
 * Date  : Aug 29, 2015
 */
package com.rd.adchallenge.ui.admin;

import java.util.Collection;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.rd.adchallenge.entities.AccountEntity;
import com.rd.adchallenge.entities.AccountEntityRepository;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;

public class AccountTableView extends AbstractTableAdminView<AccountEntity, Long> {
  
  private static final long serialVersionUID = -5367092298608296366L;
  
  private final AccountEntityRepository repository;

  public AccountTableView(AccountEntityRepository repository) {
    super(AccountEntity.class);
    
    if (repository == null) 
      throw new IllegalArgumentException("repository");
    
    this.repository = repository;
    
    setContent();
  }

  @Override
  protected String getTopLabelCaption() {
    return "Accounts created since last app startup";
  }

  @Override
  protected BeanIdResolver<Long, AccountEntity> getBeanIdResolver() {
    return b -> b.getId();
  }

  @Override
  protected Object[] getVisibleColumns() {
    return new String[] { "id", "stateName", "editionCode", "commaSeparatedAssignedUserFullNames" };
  }

  @Override
  protected Collection<AccountEntity> getCurrentBeans() {
    return Lists.newArrayList(repository.findAll());
  }
  
}

