package com.mobcolor.ms.timingTask;

import com.mobcolor.ms.timingTask.enums.TimingTaskStatus;
import com.mobcolor.ms.timingTask.enums.TimingTaskType;
import com.mobcolor.ms.timingTask.service.TimingTaskExecRecordService;
import com.mobcolor.ms.timingTask.service.TimingTaskService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.Constants;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.lang.reflect.Method;


/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 * 注：子类必须继承实现两个构造方法
 */
public class TimingTaskRunnable implements Runnable {
    /**
     * logger class
     */
    private static final Logger logger = LoggerFactory.getLogger(TimingTaskRunnable.class);

    private TimingTaskExecRecordService timingTaskExecRecordService;

    private TimingTaskService timingTaskService;


    /**
     * 任务id
     */
    @Getter
    private String taskId;

    /**
     * 任务类型
     */
    @Getter
    private TimingTaskType timingTaskType;

    /**
     * 执行表达式
     */
    @Getter
    private String execModel;

    /**
     * 被调用的bean本身
     */
    private Object execBean;

    /**
     * 被调用的方法
     */
    private Method execMethod;


    /**
     * 添加一个任务
     * A:参数为空时创建一个立即执行的触发任务
     * B:创建一个定时任务（定时器／定时任务）
     *
     * @param timingTaskType
     * @param execModel           定时器:日期字符串 格式（yyyy-MM-dd HH:mm:ss）/定时任务：cron表达式
     *                            【注：cron 不支持7位 ， cron 不支持7位，cron 不支持7位   】
     *                            示例1：
     *                            {
     *                            timingTaskExec:TimingTaskExec.TIMERS
     *                            execModel:"2017-02-03 12:22:33"
     *                            }
     *                            示例2:
     *                            {
     *                            timingTaskExec:TimingTaskExec.TIMER_TASK
     *                            execModel:"02 03 ? 04 ? ?"
     *                            }
     */

    public TimingTaskRunnable(ApplicationContext applicationContext,String taskId, String execModel, TimingTaskType timingTaskType, String beanName, String methodName) throws NoSuchMethodException {

        try {
            init(applicationContext,beanName, methodName);
            this.taskId = taskId;
            this.execModel = execModel;
            this.timingTaskType = timingTaskType;
        } catch (Exception e) {
            logger.error("加载任务接口失败", e);
            throw new BusinessException("加载任务接口失败");
        }

    }

    /**
     * 初始化准备
     */
    private void init(ApplicationContext applicationContext,String beanName, String methodName) throws NoSuchMethodException {
        this.timingTaskService = (TimingTaskService) applicationContext.getBean("timingTaskServiceImpl");
        this.timingTaskExecRecordService = (TimingTaskExecRecordService) applicationContext.getBean("timingTaskExecRecordServiceImpl");
        this.execBean = applicationContext.getBean(beanName);
        this.execMethod = execBean.getClass().getMethod(methodName, String.class);
    }

    @Override
    public void run() {
        try {

            /**
             * 定时器：只执行一次，执行完成即执行成功
             * 定时任务：循环执行，执行完成又会变成等待执行
             */
            logger.info("[TimingTaskRunnable]: the timing task(" + taskId + ") start runing ....");

            execMethod.invoke(execBean, taskId);

            if (!this.timingTaskType.equals(TimingTaskType.TIMEING_EXECUTION)) {
                updateTaskStatus(TimingTaskStatus.EXECUTION_SUCCEED);
            }
            timingTaskExecRecordService.saveTimingTaskErrorLog(taskId, "successful", Constants.SUCCESS);
            logger.info("[TimingTaskRunnable]: the timing task(" + taskId + ") is successful ....");
        } catch (Exception e) {
            updateTaskStatus(TimingTaskStatus.EXECUTION_FAILED);
            timingTaskExecRecordService.saveTimingTaskErrorLog(taskId, BaseUtils.getStackTrace(e), Constants.FAIL);
            logger.error("[TimingTaskRunnable-error]: the timing task(\"+taskId+\") is failed ....", e);
        }
    }

    /**
     * 更改任务状态
     *
     * @param timingTaskStatus
     */
    public void updateTaskStatus(TimingTaskStatus timingTaskStatus) {
        timingTaskService.updateTaskStaus(taskId, timingTaskStatus);
    }


}
