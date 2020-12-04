package com.e17cn2.threetree.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Round implements Serializable {
    private static final long serialVersionUID = 2L;
    private List<PlayerRound> playerRoundList;
    private Player winner;
}
