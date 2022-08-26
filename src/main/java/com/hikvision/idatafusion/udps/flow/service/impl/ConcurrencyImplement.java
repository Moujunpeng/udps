package com.hikvision.idatafusion.udps.flow.service.impl;

import com.hikvision.idatafusion.udps.flow.dto.DivisionDTO;
import com.hikvision.idatafusion.udps.flow.service.ConcurrencyService;
import com.hikvision.idatafusion.udps.flow.util.HookThread;
import com.hikvision.idatafusion.udps.flow.util.MyThreadFactory;
import com.hikvision.idatafusion.udps.flow.util.ReentrantJob;
import com.hikvision.idatafusion.udps.flow.util.ReentrantLockMine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConcurrencyImplement implements ConcurrencyService {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyImplement.class);

    private static Lock unfairLock = new ReentrantLockMine(false);

    static {
        //Runtime.getRuntime().addShutdownHook(new HookThread());
    };

    private Lock lock = new ReentrantLockMine(true);

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5,
            10,
            10,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue(3),
            new MyThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public int division(DivisionDTO divisionDTO) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

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

    @Override
    public void testUnfairReentrantLock(int count) {

        log.info("start test unfair lock");

        for(int i = 0;i < count;i++){
            executorService.submit(new ReentrantJob(lock));
        }

    }

    public boolean sendOnSharedLine(String message,int time) throws InterruptedException {

        log.info("send message is " + message);

        Thread.sleep(time);

        return true;
    }

}