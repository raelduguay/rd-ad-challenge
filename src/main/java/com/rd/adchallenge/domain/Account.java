/* 
 * Author : rael
 * Date   : August 24, 2015
 */
package com.rd.adchallenge.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Minimal account management data and business logic such as account details displayed on UI.
 * Provides a few read-only methods for display purposes, all processing is triggered by Events
 * fetched from App Direct once a notification has been received.
 * <p>
 * Currently not thread-safe. A real product should evaluate multi-threading implications - for a 
 * more realistic scenario where data is backed by a DB then proper transactions + Hibernate
 * optimistic locking would automatically handle concurrent updates.
 * <p>
 * Considerations for a real application:
 * <ul>
 * <li>Business methods should be decoupled from actual event format as provided by AppDirect</li>
 * <li>Entities to be indeed stored in persistent storage through JPA/Hibernate/etc.</li>  
 * </ul>
 */
public class Account {
  
  private static final Log LOGGER = LogFactory.getLog(Account.class);
  
  private static final AtomicInteger accountCounter = new AtomicInteger();
  
  private final String id = "adChallengeAccount-" + accountCounter.incrementAndGet(); 

  private BaseAccountState state = new InitialState();
  
  private final Map<String, User> assignedUsers = new HashMap<>();
  
  private String editionCode;
  
  public String getId() {
    return id;
  }
  
  public EventProcessor getEventProcessor() {
    return state;
  }

  public String getStateName() {
    return state.getName();
  }
  
  public Collection<User> getAssignedUsers() {
    return new HashSet<>(assignedUsers.values());
  }
  
  public String getCommaSeparatedAssignedUserFullNames() {
    return assignedUsers
        .values()
        .stream()
        .map(u -> u.getFullName())
        .collect(Collectors.joining(","));
  }
  
  public String getEditionCode() {
    return editionCode;
  }

  public interface EventProcessor {
    void onOrder(Event event);
    void onAssign(Event event);
    void onUnassign(Event event);
    void onChange(Event event);
    void onCancel(Event event);
    void onNotice(Event event);
  }
  
  /**
   * GOF State Pattern for event processing
   */
  private abstract class BaseAccountState implements EventProcessor {
    public abstract String getName();
    
    public void onOrder(Event event) {
      throwInvalidResponseOnIllegalState(event);
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
          id,
          getStateName());
    }
  }
  
  private class InitialState extends BaseAccountState {
    @Override
    public String getName() {
      return "Initial";
    }

    @Override
    public void onOrder(Event event) {
      // Automatically assign creator
      User creator = event.getCreatorUser();
      assignedUsers.put(creator.getOpenId(), creator);
      editionCode = event.getEditionCode();
      
      // State transition
      state = new ActiveState();
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
      if (assignedUsers.putIfAbsent(payloadUser.getOpenId(), payloadUser) != null) {
        throw new InvalidResponseException("User [%s] already assigned to account [%s]", payloadUser.getOpenId(), id);
      }
    }

    @Override
    public void onUnassign(Event event) {
      User payloadUser = getPayloadUserOrThrowIfAbsent(event);
      if (assignedUsers.remove(payloadUser.getOpenId()) == null) {
        throw new UserNotFoundException(payloadUser, Account.this);
      }
    }

    @Override
    public void onChange(Event event) {
      editionCode = event.getEditionCode();
    }

    @Override
    public void onCancel(Event event) {
      assignedUsers.clear();
      state = new CancelledState();
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

