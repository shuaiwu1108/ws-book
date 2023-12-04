package com.shuaiwu.wsbook.dto;

import java.util.Date;
import java.util.Map;
import lombok.Data;

@Data
public class JobInfo {
    private String jobName;// 任务名称
    private String jobGroup;// 任务组名称
    private Map<String, Object> jsonParams;// 任务执行信息（在用户定时远程调用接口的时候，我们可以将接口信息封装到这个Map中）
    private String cron;// 定时任务的cron表达式
    private String timeZoneId;// 定制执行任务的时区
    private Date triggerTime;// 定时器时间（目前没用上）

    private String jobClassName;
}