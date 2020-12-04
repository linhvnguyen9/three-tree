package com.e17cn2.threetree.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Connection implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("room_id")
    private int roomId;

    @JsonProperty("player_id")
    private String playerId;

    @JsonProperty("message")
    private String message;
}
