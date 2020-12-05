package com.e17cn2.threetree.entity;

import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Card implements Serializable {
    private SuiteCard suiteCard;
    private int value;
}
