package com.e17cn2.threetree.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum SuiteCard {
  DIAMONDS(4),
  HEARTS(3),
  CLUBS(2),
  SPADE(1);

  private int numVal;

  SuiteCard(int numVal) {
    this.numVal = numVal;
  }

  public int getNumVal() {
    return numVal;
  }
}
