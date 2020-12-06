package com.e17cn2.threetree.util;

import com.e17cn2.threetree.entity.Connection;

import java.util.List;

public interface ThreadCallBack {
  void checkDealCards();
  void returnNewListPlayer(Connection connection);
  void addPlayerId(String playerId);
}
