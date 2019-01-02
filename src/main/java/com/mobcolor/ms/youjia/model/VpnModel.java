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
@ServerModel("VPN")
public class VpnModel extends BaseModel {

    /**
     * vpn类型
     */
    @ServerField(value = "TYPE",name = "vpn类型")
    private String type;

    /**
     * 账号
     */
    @ServerField(value = "ACCOUNT",name = "账号")
    private String account;

    /**
     * 密码
     */
    @ServerField(value = "PASS_WORD",name = "密码")
    private String passWord;

    /**
     * 地区
     */
    @ServerField(value = "REGION",name = "地区")
    private String region;


    /**
     * 状态
     */
    @ServerField(value = "STATE",name = "状态")
    private Account state;



    /**
     * 最后一次修改时间
     */
    @ServerField(value = "LAST_UPDATE_TIME",name = "最后一次修改时间")
    private Date lastUpdateTime;

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
