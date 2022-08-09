package com.hikvision.idatafusion.udps.quartz;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CronScheduler {

    @Value("${crontab}")
    private String crontab;

    @Bean
    public JobDetail getJobDetail2(){

        return JobBuilder.newJob(CronJob.class)
                .withIdentity("saiJob2")
                .storeDurably()
                .build();

    }

    @Bean
    public CronTrigger getTrigger2(){

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(crontab);

        return TriggerBuilder.newTrigger()
                .forJob(getJobDetail2())
                .withIdentity("saiTrigger2")
                .withSchedule(cronScheduleBuilder)
                .startNow()
                .build();
    }


}