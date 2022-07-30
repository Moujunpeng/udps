package com.hikvision.idatafusion.udps.flow.mapper;

import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import com.hikvision.idatafusion.udps.flow.model.StorageLocal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StorageDao {

    boolean insertStorage(StorageLocal storageLocal);

}
