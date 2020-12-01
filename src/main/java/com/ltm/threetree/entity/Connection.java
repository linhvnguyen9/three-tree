package com.ltm.threetree.entity;

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

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("port")
    private int port;

    @JsonProperty("player_id")
    private String playerID;

    @JsonProperty("message")
    private String message;
}
