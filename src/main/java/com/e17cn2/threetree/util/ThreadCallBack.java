package com.e17cn2.threetree.util;

import com.e17cn2.threetree.entity.Connection;

import java.util.List;

public interface ThreadCallBack {
  void checkDealCards(List<String> listPlayerId);
  void returnNewListPlayer(Connection connection, List<String> listPlayerId);
}
