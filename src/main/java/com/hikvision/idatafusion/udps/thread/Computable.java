package com.hikvision.idatafusion.udps.thread;

public interface Computable {

    Integer compute(int arg) throws InterruptedException;
}
