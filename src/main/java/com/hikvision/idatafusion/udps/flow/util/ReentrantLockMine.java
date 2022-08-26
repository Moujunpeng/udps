package com.hikvision.idatafusion.udps.flow.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMine extends ReentrantLock {

    public ReentrantLockMine(boolean fair) {
        super(fair);
    }

    @Override
    protected Collection<Thread> getQueuedThreads() {

        ArrayList<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());

        Collections.reverse(arrayList);

        return arrayList;
    }
}
