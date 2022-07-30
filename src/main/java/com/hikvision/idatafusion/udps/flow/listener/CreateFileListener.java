package com.hikvision.idatafusion.udps.flow.listener;

import com.hikvision.idatafusion.udps.event.CreateFileEvent;
import com.hikvision.idatafusion.udps.flow.service.impl.CreateFileImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CreateFileListener implements ApplicationListener<CreateFileEvent> {

    private static final Logger log = LoggerFactory.getLogger(CreateFileListener.class);

    @Autowired
    CreateFileImplement createFileImplement;

    @Override
    public void onApplicationEvent(CreateFileEvent createFileEvent) {

        log.info("start create file listener");

        createFileImplement.createLocalFile(createFileEvent);
    }
}
