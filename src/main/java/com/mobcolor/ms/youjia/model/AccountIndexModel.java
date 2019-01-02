package com.mobcolor.ms.youjia.model;

import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import com.mobcolor.ms.youjia.enums.Account;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/3/5
 */
@Data
@ServerModel("ACCOUNT_INDEX")
public class AccountIndexModel extends BaseModel {

    /**
     * app id
     */
    @ServerField(value = "APP_ID",name = "应用id")
    private String appId;

    /**
     * 账号类型
     */
    @ServerField(value = "TYPE",name = "账号类型")
    private String type;

    /**
     * 账号下标
     */
    @ServerField(value = "ACCOUNT_INDEX",name = "账号下标")
    private Integer accountIndex;


    /**
     * 创建时间
     */
    @ServerField( value = "CREATER_TIME")
    private Date createrTime;

    /**
     * 扩展字段1
     */
    @ServerField(value = "EXTEND_FIELD1",name = "扩展字段")
    private String extendField1;

    /**
     * 扩展字段2
     */
    @ServerField(value = "EXTEND_FIELD2",name = "扩展字段")
    private String extendField2;
}
