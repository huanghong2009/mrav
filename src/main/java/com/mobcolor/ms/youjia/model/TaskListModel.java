package com.mobcolor.ms.youjia.model;


import com.mobcolor.ms.youjia.enums.PatchClicksType;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskForcet;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@Data
@ServerModel("TASK_LIST")
public class TaskListModel extends BaseModel implements Cloneable{

    /**
     * 任务组id
     */
    @ServerField(value = "TASK_FORCE_ID")
    private String taskForcetId;


    /**
     * 执行组
     */
    @ServerField( value = "EXEC_GROUP")
    private String execGroup;


    /**
     * 补点击规则id
     */
    @ServerField( value = "CLICK_RULES_ID")
    private String clickRulesId;


    /**
     *补点击方式
     */
    @ServerField(value = "PATCH_CLICKS_TYPE")
    private PatchClicksType patchClicksType;

    /**
     * 任务list name
     */
    @ServerField(value = "NAME")
    private String name;

    /**
     * 应用名称
     */
    @ServerField(value = "APP_NAME")
    private String appName;

    /**
     * 服务配置
     */
    @ServerField(value ="SERVER_CONFIG")
    private String serverConfig;

    /**
     * 任务类型
     */
    @ServerField(value = "TYPE")
    private TaskList type;

    /**
     * 执行数量(新增是具体次数，留存就是 0-100 的比例)
     */
    @ServerField(value = "NUM")
    private int num;

    /**
     * 任务是否创建
     */
    @ServerField(value = "IS_CREATE")
    private String isCreate;

    /**
     * 任务是否需要回调
     */
    @ServerField(value = "IS_NEED_CALL_BACK")
    private String isNeedCallBack;
    /**
     * 任务进度
     */
    @ServerField(value = "TASK_SCHEDULE_START")
    private int taskScheduleStart;

    /**
     * 任务进度
     */
    @ServerField(value = "TASK_SCHEDULE_END")
    private int taskScheduleEnd;


    /**
     * 多次登陆任务关联的id
     */
    @ServerField( value = "REPEATEDLY_CORRELATION_ID")
    private String repeatedlyCorrelationId;

    /**
     * 多次登陆任的下标
     */
    @ServerField( value = "REPEATEDLY_INDEX")
    private int repeatedlyIndex;

    /**
     * 任务列表id,多次登陆使用
     */
    @ServerField(value = "TASK_LIST_ID")
    private String taskListId;

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


    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
