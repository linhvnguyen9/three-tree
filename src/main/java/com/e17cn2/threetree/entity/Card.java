package com.e17cn2.threetree.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;
    private SuiteCard suiteCard;
    private int value;
}
