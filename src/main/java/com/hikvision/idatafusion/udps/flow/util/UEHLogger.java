package com.hikvision.idatafusion.udps.flow.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

public class UEHLogger implements Thread.UncaughtExceptionHandler{

    private static final Logger log = LoggerFactory.getLogger(UEHLogger.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        log.info("thread terminated with exception : " + t.getName() + e);

    }
}