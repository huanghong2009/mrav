package com.mobcolor.ms.kuaima.model;


import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import com.mobcolor.ms.youjia.enums.PatchClicksType;
import com.mobcolor.ms.youjia.enums.Platform;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@Data
@ServerModel("KM_LIST")
public class KMListModel extends BaseModel{

    /**
     * 任务组id
     */
    @ServerField(value = "TASK_ID")
    private String taskId;

    /**
     * 任务list name
     */
    @ServerField(value = "NAME")
    private String name;


    /**
     * 任务平台
     */
    @ServerField( value = "PLATFORM")
    private Platform platform;

    /**
     * 关联等脚本名称（多个脚本','分隔）
     */
    @ServerField( value = "SCRIPT_NAMES")
    private String scriptNames;

    /**
     * 开始时间
     */
    @ServerField(value = "START_TIME")
    private Date startTime;

    /**
     * 开始时间
     */
    @ServerField(value = "END_TIME")
    private Date endTime;


    /**
     * 执行数量
     */
    @ServerField(value = "TOTAL")
    private int total;

    /**
     * 成功数量
     */
    @ServerField( value = "SUCCEED_NUMBER")
    private int succeedNumber;

    /**
     * 失败数量
     */
    @ServerField( value = "FAILED_NUMBER")
    private int failedNumber;

    /**
     * 执行时间
     */
    @ServerField(value = "EXEC_TIME")
    private String execTime;

    /**
     * 任务状态
     */
    @ServerField(value = "STATE")
    private TaskDetail state;

    /**
     * 创建时间
     */
    @ServerField(value = "CREATER_TIME")
    private Date createrTime;
    
    /**
     * 扩展字段1
     */
    @ServerField(value = "EXTEND_FIELD1")
    private String extendField1;
    /**
     * 扩展字段2
     */
    @ServerField(value = "EXTEND_FIELD2")
    private String extendField2;


}
