package com.hikvision.idatafusion.udps.quartz;

import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import com.hikvision.idatafusion.udps.flow.mapper.InputUserDao;
import com.hikvision.idatafusion.udps.flow.service.impl.CreateFileImplement;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CronJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(CronJob.class);

    private static int i;

    @Autowired
    private InputUserDao inputUserDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info(sdf.format(new Date()) + " Job: 执行任务中...");

        // 往数据库中插入数据
        i = i + 1;
        log.info("int i is " + i);
        InputUser inputUser = new InputUser(i,""+ i);
        ArrayList<InputUser> inputUsers = new ArrayList<>();
        inputUsers.add(inputUser);
        inputUserDao.insertIntoUser(inputUsers);

    }
}