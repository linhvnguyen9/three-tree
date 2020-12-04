package com.e17cn2.threetree.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "tree.room")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable {
  @Id
  private String id;
  private double minBet;
  private List<Round> rounds;
  private int serverPort;
}
