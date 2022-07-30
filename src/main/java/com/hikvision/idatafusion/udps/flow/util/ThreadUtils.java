package com.hikvision.idatafusion.udps.flow.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    public static ExecutorService createThreadPool(){

        ThreadPoolExecutor executor= new ThreadPoolExecutor(0,5,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(15));

        return executor;
    }

}
