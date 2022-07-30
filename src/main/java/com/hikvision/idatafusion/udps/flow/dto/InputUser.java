package com.hikvision.idatafusion.udps.flow.dto;

import lombok.Data;

@Data
public class InputUser {

    private int id;

    private String name;

    public InputUser(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
