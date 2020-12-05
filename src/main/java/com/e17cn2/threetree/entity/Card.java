package com.e17cn2.threetree.entity;

import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private SuiteCard suiteCard;
    private int value;
}
