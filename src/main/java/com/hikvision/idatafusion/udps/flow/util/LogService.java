package com.hikvision.idatafusion.udps.flow.util;

import com.hikvision.idatafusion.udps.constant.CommonConstant;
import com.hikvision.idatafusion.udps.flow.service.impl.FlowServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);

    private final BlockingQueue<String> queue;

    private final LogggerThread logggerThread;

    private boolean isShutdown;

    private int reservations;

    public void log(String message) throws InterruptedException {
        queue.put(message);
    }

    public void start(){
        logggerThread.start();
    }

    public LogService() {
        this.queue = new LinkedBlockingQueue<>(CommonConstant.loggerQueueCount);
        this.logggerThread = new LogggerThread();
    }

    private class LogggerThread extends Thread{

        @Override
        public void run() {

            try {
                while (true){
                    String loggerMessage = queue.take();
                    log.info("input logger message is " + loggerMessage);
                }
            } catch (InterruptedException e) {

            }finally {

            }

        }
    }

}
