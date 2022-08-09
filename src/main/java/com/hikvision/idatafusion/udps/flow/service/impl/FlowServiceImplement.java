package com.hikvision.idatafusion.udps.flow.service.impl;

import com.github.pagehelper.PageHelper;
import com.hikvision.idatafusion.udps.constant.CommonConstant;
import com.hikvision.idatafusion.udps.flow.controller.FlowController;
import com.hikvision.idatafusion.udps.flow.dto.CalculationInfo;
import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;
import com.hikvision.idatafusion.udps.flow.dto.UserPageDTO;
import com.hikvision.idatafusion.udps.flow.mapper.InputUserDao;
import com.hikvision.idatafusion.udps.flow.mapper.StorageDao;
import com.hikvision.idatafusion.udps.flow.model.StorageLocal;
import com.hikvision.idatafusion.udps.flow.service.FlowService;
import com.hikvision.idatafusion.udps.flow.util.LogService;
import com.hikvision.idatafusion.udps.thread.Memoizer3;
import com.hikvison.idatafusion.udps.scala.MysqlRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class FlowServiceImplement implements FlowService, InitializingBean {


    @Value("${udps.multi.process}")
    private boolean multiProcess;

    @Autowired
    private StorageDao storageDao;

    @Autowired
    private InputUserDao inputUserDao;

    private static final Logger log = LoggerFactory.getLogger(FlowServiceImplement.class);

    private static final ThreadPoolExecutor executor= new ThreadPoolExecutor(5,5,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(3)
    );

    private static Memoizer3 memoizer3 = new Memoizer3();

    private LinkedBlockingQueue<CalculationInfo> sqlInfoQueue = null;

    private static LogService logService = null;

    static {

        logService = new LogService();
        logService.start();
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
    public int compute(int id) throws Throwable {
        return memoizer3.compute(id);
    }

    @Override
    public List<InputUser> queryUserByPagenumAndsize(UserPageDTO userPageDTO) {

        PageHelper.startPage(userPageDTO.getPageNum(),userPageDTO.getPageSize());

        List<InputUser> inputUsers = inputUserDao.queryAllUser();

        return inputUsers;
    }

    @Override
    public void logWrite(String message) throws InterruptedException {
        logService.log(message);
    }

    @Override
    public void stopLogService() {
        logService.stop();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sqlInfoQueue = new LinkedBlockingQueue<>(50);
    }
}
