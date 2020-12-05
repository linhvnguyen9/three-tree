package com.e17cn2.threetree.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PlayerRound implements Serializable {
    private static final long serialVersionUID = 2L;
    private Player player;
    private Card card1;
    private Card card2;
    private Card card3;
}
