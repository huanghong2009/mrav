package com.mobcolor.ms.youjia.model;

import com.mobcolor.ms.youjia.enums.PatchClicksType;
import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.ms.youjia.enums.TaskForcet;
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
@ServerModel("TASK_FORCE")
public class TaskForcetModel extends BaseModel {

    /**
     * 原广告id
     */
    @ServerField(value = "ORIGINAL_ADVERTISEMENT_ID")
    private String originalAdvertisementId;

    /**
     * 应用名称
     */
    @ServerField(value = "APP_NAME")
    private String appName;

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
     * 服务配置
     */
    @ServerField(value ="SERVER_CONFIG")
    private String serverConfig;


    /**
     * 应用渠道id
     */
    @ServerField(value = "PLATFORM_CHANNEL_ID")
    private String platformChannelId;

    /**
     * 应用渠道名称
     */
    @ServerField(value = "PLATFORM_CHANNEL_NAME")
    private String platformChannelName;

    /**
     * 关联等脚本名称（多个脚本','分隔）
     */
    @ServerField(value = "SCRIPT_NAMES")
    private String scriptNames;


    /**
     * 任务状态
     */
    @ServerField(value = "STATE")
    private TaskForcet state;

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
