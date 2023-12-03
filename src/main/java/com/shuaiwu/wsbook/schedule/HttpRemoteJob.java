package com.shuaiwu.wsbook.schedule;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;

@Slf4j
@DisallowConcurrentExecution // 禁止并发执行job
public class HttpRemoteJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        // 工作任务名称
        String jobName = jobKey.getName();
        // 工作任务组名称
        String groupName = jobKey.getGroup();
        // JobDetail.description
        String description = jobExecutionContext.getJobDetail().getDescription();
        log.info("组{}, 任务{}", groupName, jobName);
        log.info("Description：{}", description);
        JSONObject obj = JSONUtil.parseObj(description);
        String url = obj.getStr("callUrl");
        log.info(url);
    }
}