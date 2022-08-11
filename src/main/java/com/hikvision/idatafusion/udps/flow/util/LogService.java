package com.hikvision.idatafusion.udps.flow.util;

import com.hikvision.idatafusion.udps.constant.CommonConstant;
import com.hikvision.idatafusion.udps.flow.service.impl.FlowServiceImplement;
import org.omg.CORBA.TIMEOUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class LogService {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);

    private final BlockingQueue<String> queue;

    private final LogggerThread logggerThread;

    private boolean isShutdown;

    private int reservations;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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

    public void logByExecutor(String message){
        if(executorService.isShutdown()){
            throw new IllegalStateException("logService is shut down");
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info("by executor service input logger message is " + message);

                } catch (Exception e) {
                    log.info("log executor Service is shut down");
                    return;
                }finally {

                }
                log.info("finally log executor Service is shut down");
            }
        });
    }

    public void start(){
        // 开了一个后台线程在消费队列中的日志信息，然后进行打印
        logggerThread.start();
    }

    // 关闭日志服务
    public void stop(){
        synchronized (this){
            isShutdown = true;
        }
        logggerThread.interrupt();
    }

    public void stopByexecutor(){
        executorService.shutdown();
        try {
            executorService.awaitTermination(10000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        log.info("log executor service is shutdown");
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
                    Thread.sleep(3000);
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
