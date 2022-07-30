package com.hikvision.idatafusion.udps.flow.dto;

import lombok.Data;

import java.util.Properties;

@Data
public class SqlTaskInfo {

    private int id;

    private Properties prop;

    private String url;

    private String tableName;

    // 标识统计任务的类型
    private int statisticType;

}
