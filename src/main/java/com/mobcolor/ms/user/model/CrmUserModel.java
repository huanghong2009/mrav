package com.mobcolor.ms.user.model;

import com.mobcolor.ms.user.enums.CrmEnmu;
import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import lombok.Data;

import java.util.Date;


/**
 * @author 作者 E-mail:邮件
 * @version 创建时间：2016/09/27 19:00:28
 * crm用户表业务对象
 */
@Data
@ServerModel("CRM_USER")
public class CrmUserModel extends BaseModel {
    /**
     * 用户名
     */
    @ServerField( value = "USERNAME")
    private String username;

    /**
     * 密码
     */
    @ServerField( value = "PASSWORD")
    private String password;

    /**
     * 邮箱
     */
    @ServerField( value = "EMAIL")
    private String email;

    /**
     * 盐值
     */
    @ServerField( value = "SALT")
    private String salt;

    /**
     * 状态
     */
    @ServerField( value = "STATUS")
    private CrmEnmu status;

    /**
     * 创建时间
     */
    @ServerField( value = "CREATE_TIME")
    private Date createTime;

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









