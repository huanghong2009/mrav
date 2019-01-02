package com.mobcolor.ms.timingTask.service.impl;

import com.mobcolor.ms.timingTask.model.TimingTaskExecRecordModel;
import com.mobcolor.ms.timingTask.service.TimingTaskExecRecordService;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
@Service
public class TimingTaskExecRecordServiceImpl extends BaseSupportServiceImpl implements TimingTaskExecRecordService {



    /**
     * 保存任务log
     * @param taskId 任务id
     * @param log 异常log
     */
    @Override
    public void saveTimingTaskErrorLog(String taskId, String log,String state) {
        TimingTaskExecRecordModel timingTaskExecRecordModel = new TimingTaskExecRecordModel();
        timingTaskExecRecordModel.setTaskId(taskId);
        timingTaskExecRecordModel.setLog(log);
        timingTaskExecRecordModel.setState(state);
        timingTaskExecRecordModel.setTime(new Date());
        this.getMongoDao().insert(timingTaskExecRecordModel);
    }
}
