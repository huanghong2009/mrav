package com.mobcolor.ms.timingTask.service.impl;

import com.mobcolor.ms.timingTask.enums.TimingTaskStatus;
import com.mobcolor.ms.timingTask.enums.TimingTaskType;
import com.mobcolor.ms.timingTask.model.TimingTaskModel;
import com.mobcolor.ms.timingTask.service.TimingTaskService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.dao.BaseSupportServiceImpl;
import com.mobcolor.framework.utils.PrimaryUtil;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
@Service
public class TimingTaskServiceImpl extends BaseSupportServiceImpl implements TimingTaskService {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TimingTaskServiceImpl.class);

    /**
     * 获取任务列表[不传参，默认查询待执行的任务]
     *
     * @param status 任务状态
     * @return
     */
    @Override
    public List<TimingTaskModel> getTimingTasks(TimingTaskStatus status) {
        Query query = new Query();
        /**
         * 默认查询待执行的任务
         */
        String statusCode = "";
        if (status != null) {
            statusCode = status.name();
            query.addCriteria(new Criteria("status").is(statusCode));
        } else {
            List<String> state = new ArrayList<>();
            state.add(TimingTaskStatus.EXECUTION_WAIT.name());
            state.add(TimingTaskStatus.EXECUTION_CYCLE.name());
            query.addCriteria(new Criteria("status").in(state));
        }
        return this.getMongoDao().getTemplate().find(query, TimingTaskModel.class, TimingTaskModel.collection);
    }

    @Override
    public TimingTaskModel getTimingTask(String taskId) {
        try {
            return this.getMongoDao().findById(TimingTaskModel.class, taskId);
        } catch (Exception e) {
            logger.error("查询任务失败", e);
            throw new BusinessException("查询任务失败");
        }
    }

    /**
     * 检查任务是否存在
     *
     * @param beanName   bean name
     * @param methodName 方法名
     * @return
     */
    @Override
    public boolean checkTimingIsExist(String beanName, String methodName) {
        Query query = new Query();
        List<String> status = new ArrayList<>();
        status.add(TimingTaskStatus.EXECUTION_WAIT.name());
        status.add(TimingTaskStatus.EXECUTION_CYCLE.name());
        query.addCriteria(new Criteria("beanName").is(beanName));
        query.addCriteria(new Criteria("methodName").is(methodName));
        query.addCriteria(new Criteria("status").in(status));
        List<TimingTaskModel> timingTaskModelList = this.getMongoDao().getTemplate().find(
                query, TimingTaskModel.class, TimingTaskModel.collection);
        if (timingTaskModelList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * add一个任务
     *
     * @param type       任务类型
     * @param execModel  任务执行方式 （定时器／定时任务：发送时间／cron）
     * @param beanName   需要被执行的bean的name
     * @param methodName 需要被调用的方法名
     * @return 任务id
     */
    public String addTimingTasks(TimingTaskType type, String execModel, String beanName, String methodName) throws BusinessException {


        String taskId = PrimaryUtil.getId();
        String staus = "";

        if (type.equals(TimingTaskType.TIMEING_EXECUTION)) {
            staus = TimingTaskStatus.EXECUTION_CYCLE.name();
        } else {
            staus = TimingTaskStatus.EXECUTION_WAIT.name();
        }

        TimingTaskModel timingTask = new TimingTaskModel();

        timingTask.setId(taskId);
        timingTask.setExecModel(execModel);
        timingTask.setBeanName(beanName);
        timingTask.setMethodName(methodName);
        timingTask.setStatus(staus);
        timingTask.setTaskType(type.name());
        timingTask.setTime(new Date());

        this.getMongoDao().insert(timingTask);

        return taskId;
    }

    /**
     * 修改任务状态
     *
     * @param taskId 任务id
     * @param status 任务状态
     */
    @Override
    public void updateTaskStaus(String taskId, TimingTaskStatus status) {
        Query query = new Query();
        query.addCriteria(new Criteria("taskId").is(taskId));
        Update update = new Update();
        update.set("status", status.name());

        this.getMongoDao().getTemplate().updateFirst(query, update, TimingTaskModel.collection);

    }


}
