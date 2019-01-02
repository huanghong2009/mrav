package com.mobcolor.ms.youjia.model;

import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/1/5
 */
@Data
@ServerModel("ADVERTISEMENT")
public class AdvertisementModel extends BaseModel {

    /**
     * 原广告id
     */
    @ServerField( value = "ORIGINAL_ADVERTISEMENT_ID")
    private String originalAdvertisementId;

    /**
     * 应用名称
     */
    @ServerField( value = "APP_NAME")
    private String appName;


    /**
     * 应用渠道id
     */
    @ServerField( value = "PLATFORM_CHANNEL_ID")
    private String platformChannelId;

    /**
     * 应用渠道名称
     */
    @ServerField( value = "PLATFORM_CHANNEL_NAME")
    private String platformChannelName;

    /**
     * 关联等脚本名称（多个脚本','分隔）
     */
    @ServerField( value = "SCRIPT_NAMES")
    private String scriptNames;

    /**
     * json array 留存模版
     * 留存比例数组 示例: [50,20,35,50,50...]
     */
    @ServerField( value = "TASK_LISTS")
    private String taskLists;

    /**
     * 服务配置
     */
    @ServerField( value = "SERVER_CONFIG")
    private String serverConfig;


    /**
     * 补点击规则id
     */
    @ServerField( value = "CLICK_RULES_ID")
    private String clickRulesId;

    /**
     * 最小补点击数量
     */
    @ServerField( value = "MIN_CLICK_NUM")
    private Integer minClickNum;

    /**
     * 是否需要多次登陆
     */
    @ServerField( value = "IS_NEED_REPEATEDLY")
    private String isNeedRepeatedly;

    /**
     * 多次登陆天数
     */
    @ServerField(value = "REPEATEDLY_DAYS")
    private String repeatedlyDays;


    /**
     * 多次登陆模版
     */
    @ServerField(value = "REPEATEDLY_CONFIG")
    private String repeatedlyConfig;


    /**
     * 转换率
     */
    @ServerField( value = "CONVERSION_RATE")
    private Integer conversionRate;

    /**
     * 新增执行组
     */
    @ServerField( value = "ADD_EXEC_GROUP")
    private String addExecGroup;

    /**
     * 留存执行组
     */
    @ServerField( value = "RETAINED_EXEC_GROUP")
    private String retainedExecGroup;

    /**
     * 状态
     */
    @ServerField( value = "STATE")
    private String state;

    /**
     * 创建时间
     */
    @ServerField( value = "CREATER_TIME")
    private Date createrTime;

    /**
     * 扩展字段1
     */
    @ServerField( value = "EXTEND_FIELD1")
    private String extendField1;

    /**
     * 扩展字段2
     */
    @ServerField( value = "EXTEND_FIELD2")
    private String extendField2;

}
