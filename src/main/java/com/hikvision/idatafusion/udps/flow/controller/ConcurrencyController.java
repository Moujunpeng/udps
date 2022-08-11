package com.hikvision.idatafusion.udps.flow.controller;

import com.hikvision.idatafusion.udps.flow.dto.SqlTaskInfo;
import com.hikvision.idatafusion.udps.flow.service.FlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/concurrencytest")
public class ConcurrencyController {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyController.class);

    @Autowired
    FlowService flowService;

    @RequestMapping(value = "/logthread/logger",method = RequestMethod.POST)
    @ResponseBody
    public String MysqlRunner(@RequestBody String logmessage){
        log.info("input calculation is " + logmessage);
        try {
            flowService.logWrite(logmessage);
        } catch (InterruptedException e) {

        }catch (IllegalStateException e){
            return "logservice is shut down";
        }
        return logmessage;
    }

    @RequestMapping(value = "/logthread/stop",method = RequestMethod.POST)
    @ResponseBody
    public String stopLogThread(){
        log.info("stop log thread");
        flowService.stopLogService();
        String output = "thread is shutdown";
        return output;
    }

    @RequestMapping(value = "/log/loggerStartExecutor",method = RequestMethod.POST)
    @ResponseBody
    public String logStart(@RequestBody String logmessage){
        log.info("input calculation is " + logmessage);
        try {
            flowService.logWriteByExecutor(logmessage);
        } catch (IllegalStateException e){
            return "logservice is shut down";
        }
        return logmessage;
    }

    @RequestMapping(value = "/log/logStopExecutor",method = RequestMethod.POST)
    @ResponseBody
    public String stopLogExecutor(){
        log.info("stop log thread");
        flowService.stopLogServiceExecutor();
        String output = "thread is shutdown";
        return output;
    }

}
