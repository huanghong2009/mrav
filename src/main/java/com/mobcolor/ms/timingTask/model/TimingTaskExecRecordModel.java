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
@ServerModel("task_exec_record")
public class TimingTaskExecRecordModel extends BaseModel {

    /**
     * mongo collection
     */
    public static final String collection = "task_exec_record";

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 异常栈日志
     */
    private String log;


    /**
     * 状态
     */
    private String state;
    /**
     * 错误时间
     */
    private Date time;

}
