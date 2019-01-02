package com.mobcolor.ms.supplementaryClicks.cake.model;


import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/1/23
 */
@Data
@ServerModel("CAKE_SUPPLEMENTARY_CLICK")
public class CakeSupplementaryClickModel extends BaseModel {

    /**
     * 任务列表id
     */
    @ServerField(value = "TASK_LIST_ID",name = "任务列表id")
    private String taskListId;

    /**
     * 原广告id
     */
    @ServerField( value = "ORIGINAL_ADVERTISEMENT_ID",name = "原广告id")
    private String originalAdvertisementId;


    /**
     * 转换率
     */
    @ServerField( value = "CONVERSION_RATE",name = "转换率")
    private Integer conversionRate;

    /**
     * 任务进度
     */
    @ServerField(value = "TASK_SCHEDULE_START",name = "开始进度")
    private Integer scheduleStart;

    /**
     * 任务进度
     */
    @ServerField(value = "TASK_SCHEDULE_END",name = "结束进度")
    private Integer scheduleEnd;


    /**
     * 点击连接
     */
    @ServerField(value = "CLICK_URL",name = "点击连接")
    private String clickUrl;

    /**
     * 状态
     */
    @ServerField(value = "STATE",name = "状态")
    private SupplementaryClick state;

    /**
     * 创建时间
     */
    @ServerField(value = "CREATER_TIME",name = "创建时间")
    private Date createrTime;

    /**
     * 修改时间
     */
    @ServerField(value = "UPDATE_TIME",name = "修改时间")
    private Date updateTime;

    /**
     * 扩展字段1
     */
    @ServerField(value = "EXTEND_FIELD1",name = "扩展字段1")
    private String extendField1;

    /**
     * 扩展字段2
     */
    @ServerField(value = "EXTEND_FIELD2",name = "扩展字段2")
    private String extendField2;
}
