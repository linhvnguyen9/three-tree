package com.ltm.threetree.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class Round implements Serializable {
    @Id
    private String id;
    private List<PlayerRound> playerRoundList;
    private Player winner;
}
