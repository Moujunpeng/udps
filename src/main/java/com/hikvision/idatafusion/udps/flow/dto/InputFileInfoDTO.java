package com.hikvision.idatafusion.udps.flow.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InputFileInfoDTO {

    @ApiModelProperty("文件路径")
    private String path;

    @ApiModelProperty("文件名称")
    private String fileName;

}