package com.hikvision.idatafusion.udps.flow.listener;

import com.hikvision.idatafusion.udps.event.CreateFileEvent;
import com.hikvision.idatafusion.udps.flow.controller.FlowController;
import com.hikvision.idatafusion.udps.flow.mapper.InputUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UdpsStartedListener implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(UdpsStartedListener.class);

    @Value("${create.file.flag}")
    private boolean isFileCreate;


    @Value("${create.file.path}")
    private String createFilePath;

    @Value("${create.file.name}")
    private String createFileName;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("udpstarted listener");

        if(isFileCreate){
            // 发布事件
            applicationContext.publishEvent(new CreateFileEvent(this, createFilePath,createFileName));
        }
    }


}