package com.ltm.threetree.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card implements Serializable {
  private SuiteCard suiteCard;
  private List<Integer> values = new ArrayList<>();

  public Card(SuiteCard suiteCard) {
    this.suiteCard = suiteCard;
    this.values = Arrays.asList(1,2,3,4,5,6,7,8,9);
  }
}
