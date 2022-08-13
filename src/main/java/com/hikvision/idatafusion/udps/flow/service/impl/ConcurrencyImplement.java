package com.hikvision.idatafusion.udps.flow.service.impl;

import com.hikvision.idatafusion.udps.flow.dto.DivisionDTO;
import com.hikvision.idatafusion.udps.flow.service.ConcurrencyService;
import com.hikvision.idatafusion.udps.flow.util.MyThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ConcurrencyImplement implements ConcurrencyService {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyImplement.class);


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

}