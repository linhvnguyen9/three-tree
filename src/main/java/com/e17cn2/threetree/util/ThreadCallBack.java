package com.e17cn2.threetree.util;

import com.e17cn2.threetree.entity.Connection;

import java.io.ObjectOutputStream;
import java.util.List;

public interface ThreadCallBack {
  void checkDealCards(List<String> listPlayerId);
  void  returnNewListPlayer(Connection connection,
                           ObjectOutputStream outToClient, List<String> listPlayerId);
}
