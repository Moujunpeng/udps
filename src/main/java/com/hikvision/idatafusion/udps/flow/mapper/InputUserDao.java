package com.hikvision.idatafusion.udps.flow.mapper;

import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface InputUserDao {

    boolean insertIntoUser(List<InputUser> inputUsers);

    boolean deleteuserByUserId(int id);

    boolean updateByUserId(InputUser user);

    List<InputUser> queryByUserId(int id);
}
