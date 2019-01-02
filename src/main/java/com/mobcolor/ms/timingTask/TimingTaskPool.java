package com.mobcolor.ms.timingTask;

import com.mobcolor.ms.timingTask.enums.TimingTaskStatus;
import com.mobcolor.ms.timingTask.enums.TimingTaskType;
import com.mobcolor.ms.timingTask.model.TimingTaskModel;
import com.mobcolor.ms.timingTask.service.TimingTaskExecRecordService;
import com.mobcolor.ms.timingTask.service.TimingTaskService;
import com.mobcolor.framework.common.BusinessException;
import com.mobcolor.framework.utils.BaseUtils;
import com.mobcolor.framework.utils.Constants;
import com.mobcolor.framework.utils.ZKLogger;
import com.mobcolor.framework.utils.ZKLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
@Component()
public class TimingTaskPool implements ApplicationListener<ContextRefreshedEvent> {
    private static final ZKLogger logger = ZKLoggerFactory.getLogger(TimingTaskPool.class);

    private ApplicationContext applicationContext;

    /**
     * 定时任务线程池
     */
    volatile private static ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 定时任务列表
     */
    private HashMap<String, ScheduledFuture> scheduledFutureHashMap;

    /**
     * task任务列表
     */
    private HashMap<String, TimingTaskRunnable> taskMap;

    @Autowired
    private TimingTaskService timingTaskService;

    @Autowired
    private TimingTaskExecRecordService timingTaskExecRecordService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 加载任务开关
     */
    private static boolean isLoadTask = false;

    /**
     * 线程池的单例
     *
     * @return
     */
    public static ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        try {
            if (threadPoolTaskScheduler == null) {
                /**
                 * 创建实例之前可能会有一些准备性的耗时工作
                 */
                Thread.sleep(300);
                synchronized (TimingTaskPool.class) {
                    if (threadPoolTaskScheduler == null) {
                        logger.info("-----------{}:{}:定时任务线程池初始化开始-----------------",sdf.format(new Date()),new Date().getTime());
                        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
                        /**
                         * 设置线程池大小和初始化
                         */
                        threadPoolTaskScheduler.setPoolSize(10);
                        threadPoolTaskScheduler.initialize();
                        logger.info("-----------{}:{}:定时任务线程池初始化完成-----------------",sdf.format(new Date()),new Date().getTime());
                    }
                }
            }
        } catch (Exception e) {
            logger.error(":", e);
        }
        return threadPoolTaskScheduler;
    }

    public TimingTaskPool() {
        scheduledFutureHashMap = new HashMap<>();
        taskMap = new HashMap<>();
        getThreadPoolTaskScheduler();
    }




    /**
     * 添加一个任务
     * 在此郑重说明，不支持 7位的cron表达式
     *
     * @param timingTaskRunnable
     * @return taskId 任务id
     */
    public String addTimeTask(TimingTaskRunnable timingTaskRunnable) throws Exception {
        try {
            String taskId = timingTaskRunnable.getTaskId();
            ScheduledFuture scheduledFuture = null;

            /**
             * A:[立即执行会在任务添加后2s执行]
             * B:timingTaskRunnable.execmodel [TimingTaskExecModel&&time/cron]
             */
            String execModels = timingTaskRunnable.getExecModel();
            TimingTaskType taskType = timingTaskRunnable.getTimingTaskType();

            if (TimingTaskType.DIRECT_EXECUTION.equals(taskType)) {
                scheduledFuture = getThreadPoolTaskScheduler().schedule(timingTaskRunnable, new Date(System.currentTimeMillis() + 2000));
            } else if (TimingTaskType.TIMER_EXECUTION.equals(taskType)) {
                scheduledFuture = getThreadPoolTaskScheduler().schedule(timingTaskRunnable, sdf.parse(execModels));
            } else {
                scheduledFuture = getThreadPoolTaskScheduler().schedule(timingTaskRunnable, new CronTrigger(execModels));
            }

            scheduledFutureHashMap.put(taskId, scheduledFuture);
            taskMap.put(taskId, timingTaskRunnable);



            return taskId;
        } catch (Exception e) {
            logger.error("添加任务失败", e);
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 加载全部任务放入任务线程池
     */
    public void loadTasks() {
        List<TimingTaskModel> tasks = timingTaskService.getTimingTasks(null);
        for (TimingTaskModel task : tasks) {
            try {

                TimingTaskRunnable timingTaskRunnable = new TimingTaskRunnable(
                        applicationContext,
                        task.getId(),
                        task.getExecModel(),
                        TimingTaskType.valueOf(task.getTaskType()),
                        task.getBeanName(),
                        task.getMethodName());

                addTimeTask(timingTaskRunnable);

            } catch (Exception e) {
                timingTaskExecRecordService.saveTimingTaskErrorLog(task.getId(), BaseUtils.getStackTrace(e), Constants.FAIL);
                logger.error("-----------任务读取加载失败:{} --------------",task.getId());
                continue;
            }
        }
    }

    public void loadTask(String taskId){
        TimingTaskModel taskModel = timingTaskService.getTimingTask(taskId);
        try {
            TimingTaskRunnable timingTaskRunnable = new TimingTaskRunnable(
                    applicationContext,
                    taskModel.getId(),
                    taskModel.getExecModel(),
                    TimingTaskType.valueOf(taskModel.getTaskType()),
                    taskModel.getBeanName(),
                    taskModel.getMethodName());

            addTimeTask(timingTaskRunnable);
        } catch (Exception e) {
            logger.error("加载任务失败", e);
            throw new BusinessException("加载任务失败");
        }

    }

    /**
     * 取消任务
     *
     * @param taskId 任务id
     * @return
     */
    public boolean cancelTask(String taskId) {
        boolean status = scheduledFutureHashMap.get(taskId).cancel(false);
        if (status) {
            taskMap.get(taskId).updateTaskStatus(TimingTaskStatus.EXECUTION_CANCEL);
        }
        return status;
    }

    /**
     * 查看任务是否完成
     *
     * @param taskId 任务id
     * @return
     */
    public boolean isDone(String taskId) {
        return scheduledFutureHashMap.get(taskId).isDone();
    }


    /**
     * 系统启动完成后执行此方法，加载全部任务
     *
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.applicationContext = contextRefreshedEvent.getApplicationContext();
        loadTasks();
    }
}
