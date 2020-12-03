package com.ltm.threetree.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "tree.player")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Player implements Serializable {
  @Id
  private String id;

  @Field("username")
  private String username;

  @Field("password")
  private String password;

  private double money;

  private PlayerStatus playerStatus;
}
