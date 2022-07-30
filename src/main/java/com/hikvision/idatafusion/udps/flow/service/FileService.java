package com.hikvision.idatafusion.udps.flow.service;

import com.hikvision.idatafusion.udps.flow.dto.CalculationInfo;
import com.hikvision.idatafusion.udps.flow.dto.InputFileInfoDTO;
import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;

import java.util.List;

public interface FileService {

    public void createFile(InputFileInfoDTO inputFileInfoDTO);

    public void compute(CalculationInfo inputFileInfoDTO);

    public boolean insertIntoUser(List<InputUser> users);

    public boolean deleteUser(int userId);

    public void executeSql(SqlTaskInfo sqlTaskInfo);

    public boolean updateUser(InputUser user);
}
