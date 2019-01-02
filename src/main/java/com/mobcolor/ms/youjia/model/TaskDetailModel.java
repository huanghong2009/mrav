package com.mobcolor.ms.youjia.model;


import com.mobcolor.ms.youjia.enums.PatchClicksType;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import lombok.Data;
import org.json.JSONObject;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
@Data
@ServerModel("TASK_DETAIL")
public class TaskDetailModel extends BaseModel {

    /**
     * 任务组id
     */
    @ServerField(value = "TASK_FORCE_ID")
    private String taskForcetId;

    /**
     * 任务列表id
     */
    @ServerField(value = "TASK_LIST_ID")
    private String taskListId;

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
     * 执行组
     */
    @ServerField( value = "EXEC_GROUP")
    private String execGroup;

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
     * 任务状态
     */
    @ServerField(value = "STATE")
    private TaskDetail state;

    /**
     * 是否收到回调
     */
    @ServerField(value = "IS_CALL_BACK")
    private String isCallBcak;

    /**
     * 关联等脚本名称（多个脚本','分隔）
     */
    @ServerField(value = "SCRIPT_NAMES")
    private String scriptNames;

    /**
     * 设备
     */
    @ServerField(value = "DEVICE")
    private String device;

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
     * 其他信息
     */
    @ServerField(value = "OTHER_INFO")
    private String otherInfo;

    /**
     * 最后修改时间
     */
    @ServerField(value = "UPDATE_TIME")
    private Date updateTime;

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
