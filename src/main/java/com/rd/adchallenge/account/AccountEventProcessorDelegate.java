/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.account;

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rd.adchallenge.entities.AccountEntity;
import com.rd.adchallenge.entities.AccountEntityRepository;
import com.rd.adchallenge.entities.User;
import com.rd.adchallenge.event.Event;
import com.rd.adchallenge.event.InvalidResponseException;
import com.rd.adchallenge.event.UserNotFoundException;

/**
 * Ties a persistent account entity with related event processing logic.
 */
public class AccountEventProcessorDelegate {
  
  private static final Log LOGGER = LogFactory.getLog(AccountEventProcessorDelegate.class);
  
  private final AccountEntityRepository repository;
  
  private AccountEntity entity;
  
  private BaseAccountState state;

  public AccountEventProcessorDelegate(AccountEntityRepository repository, AccountEntity entity) {
    super();
    this.repository = repository;
    this.entity = entity;
    setState(createStateFromEntity());
  }
  
  protected BaseAccountState createStateFromEntity() {
    if (StringUtils.isBlank(entity.getStateName())) {
      return new InitialState();
    }
    
    try {
      Class<?> stateClass = Class.forName(entity.getStateName());
      Constructor<?> stateClassCtor = stateClass.getConstructor(AccountEventProcessorDelegate.class);
      return (BaseAccountState)stateClassCtor.newInstance(this);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException("Cannot create state from name [" + entity.getStateName() + "]", e);
    }
  }
  
  protected void setState(BaseAccountState state) {
    this.state = state;
    entity.setStateName(state.getClass().getName());
  }
  
  public AccountEventProcessor getEventProcessor() {
    return state;
  }
  
  /**
   * GOF State Pattern for event processing
   */
  private abstract class BaseAccountState implements AccountEventProcessor {
    public abstract String getName();
    
    public Long onOrder(Event event) {
      throwInvalidResponseOnIllegalState(event);
      return null;
    }

    public void onAssign(Event event) {
      throwInvalidResponseOnIllegalState(event);
    }
    
    public void onUnassign(Event event) {
      throwInvalidResponseOnIllegalState(event);
    }
    
    public void onChange(Event event) {
      throwInvalidResponseOnIllegalState(event);
    }
    
    public void onCancel(Event event) {
      throwInvalidResponseOnIllegalState(event);
    }
    
    public void onNotice(Event event) {
      throwInvalidResponseOnIllegalState(event);
    }
    
    protected void throwInvalidResponseOnIllegalState(Event event) {
      throw new InvalidResponseException(
          "Cannot process event [%s] for account [%s] in state [%s]", 
          event.getEventType(), 
          entity.getId(),
          entity.getStateName());
    }
  }
  
  private class InitialState extends BaseAccountState {
    @Override
    public String getName() {
      return "Initial";
    }

    @Override
    public Long onOrder(Event event) {
      // Automatically assign creator
      User creator = event.getCreatorUser();
      entity.getAssignedUsers().add(creator);
      entity.setEditionCode(event.getEditionCode());
      
      // State transition
      setState(new ActiveState());
      
      entity = repository.save(entity);
      return entity.getId();
    }
  }
  
  private class ActiveState extends BaseAccountState {
    @Override
    public String getName() {
      return "Active";
    }

    @Override
    public void onAssign(Event event) {
      User payloadUser = getPayloadUserOrThrowIfAbsent(event);
      if (!entity.getAssignedUsers().add(payloadUser)) {
        throw new InvalidResponseException("User [%s] already assigned to account [%s]", payloadUser.getOpenId(), entity.getId());
      }
    }

    @Override
    public void onUnassign(Event event) {
      User payloadUser = getPayloadUserOrThrowIfAbsent(event);
      if (!entity.getAssignedUsers().remove(payloadUser.getOpenId())) {
        throw new UserNotFoundException(payloadUser, entity.getId());
      }
    }

    @Override
    public void onChange(Event event) {
      entity.setEditionCode(event.getEditionCode());
    }

    @Override
    public void onCancel(Event event) {
      entity.getAssignedUsers().clear();
      setState(new CancelledState());
    }

    @Override
    public void onNotice(Event event) {
      LOGGER.info("Notice not implemented for this challenge - noop");
    }

    private User getPayloadUserOrThrowIfAbsent(Event event) {
      return event
          .getPayloadUser()
          .orElseThrow(() -> new InvalidResponseException("No user in event payload"));
    }
  }
  
  private class CancelledState extends BaseAccountState {
    @Override
    public String getName() {
      return "Cancelled";
    }
  }

}

