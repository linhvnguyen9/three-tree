package com.e17cn2.threetree.entity;

public enum SuiteCard {
  DIAMONDS(4),
  HEARTS(3),
  SPADE(2),
  CLUBS(1);

  private int numVal;

  SuiteCard(int numVal) {
    this.numVal = numVal;
  }

  public int getNumVal() {
    return numVal;
  }
}
