package com.hikvision.idatafusion.udps.event;

import org.springframework.context.ApplicationEvent;

public class CreateFileEvent extends ApplicationEvent {

    //需要发送的具体内容
    private String filepath;

    private String filename;

    public CreateFileEvent(Object source,String filepath,String filename) {
        super(source);
        this.filepath = filepath;
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}