package com.shuaiwu.wsbook.controller;

import com.shuaiwu.wsbook.dto.JobInfo;
import com.shuaiwu.wsbook.service.JobService;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("quartz")
public class QuartzController {

    private static final String TIME_ZONE = "Asia/Shanghai";

    @Autowired
    private JobService jobService;

    @RequestMapping("create")
    public void create(){
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobName("bqg-job");
        jobInfo.setJobGroup("bqg");
        jobInfo.setTimeZoneId(TIME_ZONE);  //时区指定上海
        jobInfo.setCron("0/5 * * * * ? ");  //每5秒执行一次
        Map<String, Object> params = new HashMap<>();
        //添加需要调用的接口信息
        String callUrl = "https://www.xbiquge.bz";
        params.put("callUrl", callUrl);
        jobInfo.setJsonParams(params);
        jobService.save(jobInfo);
    }

    @RequestMapping("delete")
    public void delete(){
        jobService.remove("bqg-job","bqg");
        log.info("组[{}], 任务[{}]已删除", "test", "test-job");
    }
}
