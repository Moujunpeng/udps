package com.hikvision.idatafusion.udps.flow.service;

import com.hikvision.idatafusion.udps.flow.dto.DivisionDTO;

public interface ConcurrencyService {

    public int division(DivisionDTO divisionDTO);

    public boolean trySendOnSharedLine(String message,int time) throws InterruptedException;

    public void testUnfairReentrantLock(int count);
}