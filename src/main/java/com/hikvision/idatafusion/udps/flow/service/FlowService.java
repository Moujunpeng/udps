package com.hikvision.idatafusion.udps.flow.service;

import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;
import com.hikvision.idatafusion.udps.flow.dto.UserPageDTO;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface FlowService {

    public void executeSql(int id) throws Exception;

    public void executeSparkTask(SqlTaskInfo sqlTaskInfo);

    public String queryStatisticResult(String id);

    public int compute(int id) throws Throwable;

    public List<InputUser> queryUserByPagenumAndsize(UserPageDTO userPageDTO);

}
