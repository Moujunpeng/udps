package com.hikvision.idatafusion.udps.flow.service.impl;

import com.hikvision.idatafusion.udps.flow.dto.DivisionDTO;
import com.hikvision.idatafusion.udps.flow.service.ConcurrencyService;
import com.hikvision.idatafusion.udps.flow.util.HookThread;
import com.hikvision.idatafusion.udps.flow.util.MyThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConcurrencyImplement implements ConcurrencyService {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyImplement.class);

    static {
        //Runtime.getRuntime().addShutdownHook(new HookThread());
    };

    private Lock lock = new ReentrantLock();

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5,
            10,
            10,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue(3),
            new MyThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    @Override
    public int division(DivisionDTO divisionDTO) {

        final int[] output = {0};

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {

                output[0] = divisionDTO.getDividend()/divisionDTO.getDivisor();

            }
        });

        log.info("division calculation result is " + output[0]);

        return output[0];
    }

    @Override
    public boolean trySendOnSharedLine(String message, int time) throws InterruptedException {

        if(!lock.tryLock(time,TimeUnit.SECONDS)){
           log.info("get retreenlock failed");
           return false;
        }

        boolean flag;

        try{
            flag = sendOnSharedLine(message, time);
        }finally {
            lock.unlock();
        }

        return flag;
    }

    public boolean sendOnSharedLine(String message,int time) throws InterruptedException {

        log.info("send message is " + message);

        Thread.sleep(time);

        return true;
    }

}