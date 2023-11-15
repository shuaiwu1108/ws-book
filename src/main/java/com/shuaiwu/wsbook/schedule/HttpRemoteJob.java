package com.shuaiwu.wsbook.schedule;

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
        //工作任务名称
        String jobName = jobKey.getName();
        //工作任务组名称
        String groupName = jobKey.getGroup();
        //任务类名称(带路径)
        String classPathName = jobExecutionContext.getJobDetail().getJobClass().getName();
        //任务类名称
        String className = jobExecutionContext.getJobDetail().getJobClass().getSimpleName();
        //获取Trigger内容
        TriggerKey triggerKey = jobExecutionContext.getTrigger().getKey();
        //触发器名称
        String triggerName = triggerKey.getName();
        //出发组名称(带路径)
        String triggerPathName = jobExecutionContext.getTrigger().getClass().getName();
        //触发器类名称
        String triggerClassName = jobExecutionContext.getTrigger().getClass().getSimpleName();
        log.info("开始执行job=====》");
        log.info("工作任务名称[{}], 工作组[{}]", jobName, groupName);
        log.info("任务类名[{}], 路径[{}]", className, classPathName);
        log.info("触发器名[{}], 组[{}],组(带路径)[{}]", triggerName, triggerClassName, triggerPathName);
        log.info("结束执行job======《");
    }
}
