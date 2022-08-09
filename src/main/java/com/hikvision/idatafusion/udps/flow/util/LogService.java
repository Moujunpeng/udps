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

        // 判断日志服务是否关闭，如果没有关闭的话，则向日志队列添加信息
        synchronized (this){
            if(isShutdown){
                throw new IllegalStateException("logService is shut down");
            }
            reservations++;
        }
        queue.put(message);
    }

    public void start(){
        logggerThread.start();
    }

    // 关闭日志服务
    public void stop(){
        synchronized (this){
            isShutdown = true;
        }
        logggerThread.interrupt();
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
                    if(isShutdown && reservations == 0){
                        break;
                    }
                    String loggerMessage = queue.take();
                    reservations--;
                    log.info("input logger message is " + loggerMessage);
                }
            } catch (InterruptedException e) {
                log.info("logService is shut down");
                return;
            }finally {

            }
            log.info("finally logService is shut down");
        }
    }

}
