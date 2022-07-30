package com.hikvision.idatafusion.udps.flow.dto;

import lombok.Data;

import java.util.Properties;

@Data
public class PrestatisticInfoDTO {

    private Properties prop;

    private String table;

    private String url;

    private String sql;


}