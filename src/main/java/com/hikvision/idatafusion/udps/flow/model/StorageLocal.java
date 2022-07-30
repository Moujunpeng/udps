package com.hikvision.idatafusion.udps.flow.model;

import lombok.Data;

@Data
public class StorageLocal {

    public StorageLocal(int id, long count) {
        this.id = id;
        this.count = count;
    }

    private int id;

    private long count;
}
