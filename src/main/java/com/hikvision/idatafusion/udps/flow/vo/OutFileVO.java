package com.hikvision.idatafusion.udps.flow.vo;

import io.swagger.annotations.ApiModelProperty;

public class OutFileVO {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @ApiModelProperty("文件路径")
    private String path;

    @ApiModelProperty("文件名称")
    private String fileName;

    public OutFileVO(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }
}
