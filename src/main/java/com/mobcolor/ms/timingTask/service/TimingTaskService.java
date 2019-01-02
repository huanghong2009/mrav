package com.mobcolor.ms.timingTask.service;

import com.mobcolor.ms.timingTask.enums.TimingTaskStatus;
import com.mobcolor.ms.timingTask.enums.TimingTaskType;
import com.mobcolor.ms.timingTask.model.TimingTaskModel;
import com.mobcolor.ms.timingTask.model.dto.TimingTaskRunnableDTO;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseService;

import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
public interface TimingTaskService extends BaseService {

    /**
     * 获取任务列表
     * @param status 任务状态
     * @return
     */
    List<TimingTaskModel> getTimingTasks(TimingTaskStatus status);

    /**
     * 查询一条任务
     * @param taskId
     * @return
     */
    TimingTaskModel getTimingTask(String taskId);

    /**
     * 检查任务是否存在
     * @param beanName bean name
     * @param methodName 方法名
     * @return
     */
    boolean checkTimingIsExist(String beanName,String methodName);

    /**
     * 添加一个任务
     * @param type 任务类型
     * @param execModel 执行表达式
     * @param beanName
     * @param methodName
     * @return
     * @throws Exception
     */
    String addTimingTasks(TimingTaskType type, String execModel, String beanName, String methodName) throws BusinessException;


    /**
     * 修改任务状态
     * @param taskId 任务id
     * @param status 任务状态
     */
    void updateTaskStaus(String taskId, TimingTaskStatus status);
}
