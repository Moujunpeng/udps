package com.hikvision.idatafusion.udps.flow.service.impl;

import com.hikvision.idatafusion.udps.flow.dto.CalculationInfo;
import com.hikvision.idatafusion.udps.flow.dto.InputFileInfoDTO;
import com.hikvision.idatafusion.udps.flow.dto.InputUser;
import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;
import com.hikvision.idatafusion.udps.flow.mapper.InputUserDao;
import com.hikvision.idatafusion.udps.flow.service.FileService;
import com.hikvision.idatafusion.udps.flow.util.CreateFileByCreateProcess;
import com.hikvision.idatafusion.udps.flow.util.UdpsBoostStrap;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class FileServiceImplement implements FileService, InitializingBean {

    @Autowired
    private InputUserDao inputUserDao;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final ThreadPoolExecutor executor= new ThreadPoolExecutor(0,5,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>(15));

    private LinkedBlockingQueue<CalculationInfo> linkedBlockingQueue = null;

    private LinkedBlockingQueue<CalculationInfo> sqlQueue = null;

    @Override
    public void afterPropertiesSet() throws Exception {

        linkedBlockingQueue = new LinkedBlockingQueue<>(50);
        calculationThread calculationThread = new calculationThread();
        calculationThread.setDaemon(true);
        calculationThread.start();

    }


    class calculationThread extends Thread{

        @Override
        public void run() {
            while (true){
                try {
                    CalculationInfo take = linkedBlockingQueue.take();
                    executorService.submit(()->{
                        System.out.println("current thread name is " + Thread.currentThread().getName());
                        System.out.println("output is " + (take.getInput() * take.getInput()));
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void createFile(InputFileInfoDTO inputFileInfoDTO) {
        //linkedBlockingQueue.add(inputFileInfoDTO);
        executorService.execute(new CreateFileByCreateProcess(inputFileInfoDTO.getPath(),"file_" + inputFileInfoDTO.getFileName()));
    }

    @Override
    public void compute(CalculationInfo inputFileInfoDTO) {
        linkedBlockingQueue.add(inputFileInfoDTO);
    }

    @Override
    public boolean insertIntoUser(List<InputUser> users) {

        inputUserDao.insertIntoUser(users);

        return true;
    }

    @Override
    public boolean deleteUser(int userId) {

        inputUserDao.deleteuserByUserId(userId);

        return false;
    }

    @Override
    public void executeSql(SqlTaskInfo sqlTaskInfo) {
        UdpsBoostStrap.start(sqlTaskInfo);
    }

    @Override
    public boolean updateUser(InputUser user) {

        // 查询数据库中是否有该条记录，如果没有则直接返回false
        List<InputUser> inputUsers = inputUserDao.queryByUserId(user.getId());
        if(CollectionUtils.isEmpty(inputUsers)){
            return false;
        }

        boolean flag = inputUserDao.updateByUserId(user);
        return flag;
    }

}
