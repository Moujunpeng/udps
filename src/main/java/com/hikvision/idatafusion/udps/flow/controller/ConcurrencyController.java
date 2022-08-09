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

    @RequestMapping(value = "/thread/loggertest",method = RequestMethod.POST)
    @ResponseBody
    public String MysqlRunner(@RequestBody String logmessage) throws Exception{
        log.info("input calculation is " + logmessage);
        flowService.logWrite(logmessage);
        return logmessage;
    }

}
