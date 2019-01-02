package com.mobcolor.ms.timingTask.model;

import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ServerModel("task")
public class TimingTaskModel extends BaseModel {

    /**
     * mongo collection
     */
    public static final String collection = "task";


    /**
     * 任务执行方式,直接执行／定时执行/定时器执行
     */
    private String taskType;

    /**
     * 任务状态 待执行／执行成功/执行失败
     */
    private String status;

    /**
     * 任务执行方式 （定时器／定时任务：发送时间／cron）
     */
    private String execModel;

    /**
     * bean id
     */
    private String beanName;

    /**
     * method name
     */
    private String methodName;

    /**
     * 时间
     */
    private Date time;
}
