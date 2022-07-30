package com.hikvision.idatafusion.udps.flow.service;

import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;

public interface FlowService {

    public void executeSql(int id) throws Exception;

    public void executeSparkTask(SqlTaskInfo sqlTaskInfo);

    public String queryStatisticResult(String id);

}
