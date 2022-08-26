package com.hikvision.idatafusion.udps.flow.util;

import java.util.concurrent.locks.Lock;

public class ReentrantJob implements Runnable{

    private Lock lock;
    public ReentrantJob(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        for(int i = 0;i < 2;i++){
            lock.lock();
            try {
                Thread.sleep(1000);
                System.out.println("获取锁的当前线程[" +
                        Thread.currentThread().getName()
                        + "], 同步队列中的线程" +
                        ((ReentrantLockMine)lock).getQueuedThreads() + "");
            } catch (Exception e) {
                System.out.println("exception is " + e);
            }finally {
                lock.unlock();
            }
        }

    }

}