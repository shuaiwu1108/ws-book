package com.shuaiwu.wsbook.schedule;

import com.shuaiwu.wsbook.service.IBookCatalogService;
import com.shuaiwu.wsbook.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@DisallowConcurrentExecution // 禁止并发执行job
@Component
public class BqgBookCatalogJob implements Job {

    @Autowired
    private IBookService iBookService;
    @Autowired
    private IBookCatalogService iBookCatalogService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        // 工作任务名称
        String jobName = jobKey.getName();
        // 工作任务组名称
        String groupName = jobKey.getGroup();
        log.info("任务组：{}, 任务名称：{}", groupName, jobName);
        try {
            iBookCatalogService.saveBookCatalog(iBookService.list()); // 存储相应的章节，章节信息存储minio
        } catch (InterruptedException e) {
            log.error("BqgJob执行异常", e);
        }
    }
}