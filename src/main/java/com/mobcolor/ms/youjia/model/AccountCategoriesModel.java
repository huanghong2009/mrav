package com.mobcolor.ms.youjia.model;

import com.mobcolor.framework.common.BaseModel;
import com.mobcolor.framework.common.ServerField;
import com.mobcolor.framework.common.ServerModel;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/3/5
 */
@Data
@ServerModel("ACCOUNT_CATEGORIES")
public class AccountCategoriesModel extends BaseModel {

    /**
     * 类别名称
     */
    @ServerField(value = "NAME",name = "类别名称")
    private String name;

    /**
     * 创建时间
     */
    @ServerField(value = "CREATER_TIME",name = "创建时间")
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
