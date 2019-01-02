package com.mobcolor.ms.timingTask.model.dto;

import lombok.Data;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
@Data
public class TimingTaskRunnableDTO {
    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务执行方式,直接执行／定时执行
     */
    private String taskType;

    /**
     * 任务状态 待执行／执行成功/执行失败
     */
    private String status;

    /**
     * 任务执行cron表达式
     */
    private String execModel;
}
