package com.hikvision.idatafusion.udps.flow.controller;

import com.hikvision.idatafusion.udps.flow.dto.*;
import com.hikvision.idatafusion.udps.flow.service.FileService;
import com.hikvision.idatafusion.udps.flow.service.FlowService;
import com.hikvision.idatafusion.udps.flow.vo.OutFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flow")
public class FlowController {

    private static final Logger log = LoggerFactory.getLogger(FlowController.class);

    @Autowired
    FileService fileService;

    @Autowired
    FlowService flowService;

    @RequestMapping(value = "test",method = RequestMethod.POST)
    public String hello(){
        return "hello world";
    }

    @RequestMapping(value = "createFile",method = RequestMethod.POST)
    @ResponseBody
    public OutFileVO creatFile(@RequestBody InputFileInfoDTO inputFileInfoDTO){
        fileService.createFile(inputFileInfoDTO);
        OutFileVO outFileVO = new OutFileVO(inputFileInfoDTO.getPath(),inputFileInfoDTO.getFileName());
        return outFileVO;
    }

    @RequestMapping(value = "compute",method = RequestMethod.POST)
    @ResponseBody
    public CalculationInfo compute(@RequestBody CalculationInfo calculationInfo){
        log.info("input calculation is " + calculationInfo);
        fileService.compute(calculationInfo);
        //OutFileVO outFileVO = new OutFileVO(inputFileInfoDTO.getPath(),inputFileInfoDTO.getFileName());
        return calculationInfo;
    }

    @RequestMapping(value = "queryAllUsers",method = RequestMethod.GET)
    @ResponseBody
    public String queryUser(){
//        log.info("input calculation is " + calculationInfo);
        //fileService.compute(calculationInfo);
        //OutFileVO outFileVO = new OutFileVO(inputFileInfoDTO.getPath(),inputFileInfoDTO.getFileName());
        return "";
    }

    @RequestMapping(value = "/users/add",method = RequestMethod.POST)
    @ResponseBody
    public String insertUser(@RequestBody List<InputUser> inputUsers){
        log.info("input calculation is " + inputUsers);
        fileService.insertIntoUser(inputUsers);
        return "";
    }

    @RequestMapping(value = "/users/queryById",method = RequestMethod.POST)
    @ResponseBody
    public List<InputUser> queryUserById(@RequestParam(value="id",required=true) int id){
        //log.info("input calculation is " + inputUsers);
        //fileService.insertIntoUser(inputUsers);
        return null;
    }

    @RequestMapping(value = "/users/updateById",method = RequestMethod.POST)
    @ResponseBody
    public InputUser updateUserById(@RequestBody InputUser user){
        log.info("start update id is " + user.getId());
        boolean b = fileService.updateUser(user);
        if(!b){
            log.error("update user failed");
        }
        return user;
    }

    @RequestMapping(value = "/users/delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestParam(value="id",required=true) int id){
        log.info("input calculation is " + id);
        fileService.deleteUser(id);
        return String.valueOf(id);
    }

    @RequestMapping(value = "executeSql",method = RequestMethod.POST)
    @ResponseBody
    public String executeSql(@RequestBody SqlTaskInfo sqlTaskInfo){
        log.info("input calculation is " + sqlTaskInfo);
        fileService.executeSql(sqlTaskInfo);
        return "0000000";
    }

    @RequestMapping(value = "/executor/rejecttest",method = RequestMethod.POST)
    @ResponseBody
    public String testThreadPoolExecutor(@RequestParam(value="id",required=true) int id) throws Exception{
        log.info("input calculation is " + id);
        flowService.executeSql(id);
        return String.valueOf(id);
    }

    @RequestMapping(value = "/executor/mysqlRunner",method = RequestMethod.POST)
    @ResponseBody
    public int MysqlRunner(@RequestBody SqlTaskInfo sqlTaskInfo) throws Exception{
        log.info("input calculation is " + sqlTaskInfo);
        flowService.executeSparkTask(sqlTaskInfo);
        return sqlTaskInfo.getId();
    }

    @RequestMapping(value = "/statisticresult/query",method = RequestMethod.GET)
    @ResponseBody
    public String queryStatisticResult(@RequestParam(value="id",required=true) String id) throws Exception{
        log.info("input calculation is " + id);
        flowService.queryStatisticResult(id);
        return String.valueOf(id);
    }

    @RequestMapping(value = "/thread/cachecompute",method = RequestMethod.POST)
    @ResponseBody
    public int computeCache(@RequestParam(value="id",required=true) int id) {
        log.info("input calculation is " + id);
        int output = 0;
        try {
            output = flowService.compute(id);
        } catch (InterruptedException e) {
            log.error("compute failed " + e);
        } catch (Throwable throwable) {
            log.error("compute failed " + throwable);
        }
        log.info("output calculation result is " + output);
        return output;
    }

    @RequestMapping(value = "/inputuser/queryByPagenumAndPageSize",method = RequestMethod.POST)
    @ResponseBody
    public List<InputUser> queryByPagenumAndPageSize(@RequestBody UserPageDTO userPageDTO) {
        log.info("input start page is " + userPageDTO.getPageNum() + "page size is " + userPageDTO.getPageSize());
        List<InputUser> inputUsers = flowService.queryUserByPagenumAndsize(userPageDTO);
        return inputUsers;
    }



}