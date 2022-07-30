package com.hikvision.idatafusion.udps.flow.util;

import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;

import java.util.concurrent.*;

public class UdpsBoostStrap {

    // 控制任务达到的速率
    private static final Semaphore semaphore = new Semaphore(15);

    private static LinkedBlockingQueue<SqlTaskInfo> sqlTaskInfoQueue = new LinkedBlockingQueue<>();

    private static ExecutorService executor = new ThreadPoolExecutor(5,5,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(15));

    public synchronized static void start(SqlTaskInfo sqlTaskInfo){
        checkQueueStatus();
        sqlTaskInfoQueue.offer(sqlTaskInfo);
    }

    static {
        Thread thread = new Thread(() -> {
            handleSqlTaskInfo();
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static void checkQueueStatus(){

        try {
            boolean b = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if(!b){
                throw new IllegalStateException("task queue is full");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("task queue is full");
        }

    }

    public static void handleSqlTaskInfo(){
        while (true){
            try {
                SqlTaskInfo take = sqlTaskInfoQueue.take();
                executor.submit(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}