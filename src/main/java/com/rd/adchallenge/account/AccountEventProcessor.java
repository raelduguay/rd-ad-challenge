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
 * Date  : Aug 31, 2015
 *
 * ---------------------------------------------------------------------------
 */
package com.rd.adchallenge.account;

import com.rd.adchallenge.event.Event;

public interface AccountEventProcessor {
  /**
   * @return account id
   */
  Long onOrder(Event event);
  void onAssign(Event event);
  void onUnassign(Event event);
  void onChange(Event event);
  void onCancel(Event event);
  void onNotice(Event event);
}

