package com.hikvision.idatafusion.udps.flow.service.impl;

import com.hikvision.idatafusion.udps.constant.CommonConstant;
import com.hikvision.idatafusion.udps.flow.controller.FlowController;
import com.hikvision.idatafusion.udps.flow.dto.CalculationInfo;
import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;
import com.hikvision.idatafusion.udps.flow.mapper.StorageDao;
import com.hikvision.idatafusion.udps.flow.model.StorageLocal;
import com.hikvision.idatafusion.udps.flow.service.FlowService;
import com.hikvison.idatafusion.udps.MysqlRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class FlowServiceImplement implements FlowService, InitializingBean {


    @Value("${udps.multi.process}")
    private boolean multiProcess;

    @Autowired
    private StorageDao storageDao;

    private static final Logger log = LoggerFactory.getLogger(FlowServiceImplement.class);

    private static final ThreadPoolExecutor executor= new ThreadPoolExecutor(5,5,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(3)
    );

    private LinkedBlockingQueue<CalculationInfo> sqlInfoQueue = null;


    static {

        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

    }

    @Override
    public void executeSql(int id) throws Exception{
        try {
            executor.submit(()->{
                System.out.println("output id is " + id * id);
                try {
                    //Thread.sleep(1000000);
                    while (true){

                    }
                } catch (Exception e) {
                    log.info("exception is " + e);
                }
            });
        }catch (Exception e){
            log.info("exception is " + e);
            Exception exception = new Exception(e);
            throw exception;
        }

    }

    @Override
    public void executeSparkTask(SqlTaskInfo sqlTaskInfo) {
        try {
            executor.submit(()->{
                try {
                    if(!multiProcess){
                        log.info("start single process");
                        if(sqlTaskInfo.getStatisticType() == CommonConstant.PRESTATISTIC_TASK_TYPE.COUNT){
                            long count = MysqlRunner.statisticQuantity(sqlTaskInfo.getProp(), sqlTaskInfo.getUrl(), sqlTaskInfo.getTableName());
                            StorageLocal storageLocal = new StorageLocal(sqlTaskInfo.getId(), count);
                            storageDao.insertStorage(storageLocal);
                        }
                    }else{
                        log.info("start multi process");
                    }
                } catch (Exception e) {
                    log.info("exception is " + e);
                }
            });
        }catch (Exception e){
            log.info("exception is " + e);
        }
    }


    @Override
    public String queryStatisticResult(String id) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlInfoQueue = new LinkedBlockingQueue<>(50);
    }
}
