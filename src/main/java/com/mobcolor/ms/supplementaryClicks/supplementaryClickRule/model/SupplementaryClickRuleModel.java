package com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model;


import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
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
@ServerModel("SUPPLEMENTARY_CLICK_RULE")
public class SupplementaryClickRuleModel extends BaseModel {
    /**
     * 名称
     */
    @ServerField(value = "NAME",name = "别名")
    private String name;

    /**
     * 有位互动分配给己方的ID
     */
    @ServerField(value = "INIT_METHOD",name = "初始化的方法")
    private String initMethod;

    /**
     * 应用回调之后触发的方法
     */
    @ServerField(value = "CALL_BACK_METHOD",name ="收到回调触发的方法" )
    private String callBackMethod;

    /**
     * 状态变更触发的方法
     */
    @ServerField(value = "CHANGE_STATE_METHOD",name = "状态变更触发的方法")
    private String changeStateMethod;

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
