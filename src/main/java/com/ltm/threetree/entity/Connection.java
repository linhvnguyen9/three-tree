package com.ltm.threetree.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class Connection implements Serializable {

    private String ipAddress;
    private String hostname;
    private int port;
    private String playerID;
}
