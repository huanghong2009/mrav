package com.mobcolor.ms.timingTask.service;


import com.mobcolor.framework.dao.BaseService;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
public interface TimingTaskExecRecordService extends BaseService {

    /**
     * 保存任务的异常log
     * @param taskId 任务id
     * @param errorLog 异常log
     */
    void saveTimingTaskErrorLog(String taskId, String errorLog,String state);
}
