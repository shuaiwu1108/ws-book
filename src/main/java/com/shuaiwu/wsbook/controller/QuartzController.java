package com.shuaiwu.wsbook.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import com.shuaiwu.wsbook.dto.JobInfo;
import com.shuaiwu.wsbook.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("quartz")
public class QuartzController {

    private static final String TIME_ZONE = "Asia/Shanghai";

    @Autowired
    private JobService jobService;

    @PostMapping("create")
    public Object create(@RequestBody JSONObject jsonObject){
        JobInfo jobInfo = new JobInfo();
        jobInfo.setJobName(jsonObject.getStr("jobName"));
        jobInfo.setJobGroup(jsonObject.getStr("jobGroup"));
        jobInfo.setTimeZoneId(TIME_ZONE);  //时区指定上海
        jobInfo.setCron(jsonObject.getStr("cron"));  //每天的23点执行
        jobInfo.setJobClassName(jsonObject.getStr("jobClassName"));//"com.shuaiwu.wsbook.schedule.BqgJob"
        jobService.save(jobInfo);
        return MapUtil.builder()
            .put("message", "成功")
            .build();
    }

    @PostMapping("delete")
    public Object delete(@RequestBody JSONObject jsonObject){
        jobService.remove(jsonObject.getStr("jobName"),jsonObject.getStr("jobGroup"));
        return MapUtil.builder()
            .put("message", "成功")
            .build();
    }
}
