package com.mobcolor.ms.youjia.model;


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
@ServerModel("DEVICE_GROUP")
public class DeviceGroupModel extends BaseModel {

    /**
     * 名称
     */
    @ServerField(value = "NAME",name = "名称")
    private String name;

    /**
     * 备注
     */
    @ServerField(value = "REMARKS",name = "备注")
    private String remarks;

    /**
     * 执行组
     */
    @ServerField(value = "EXEC_GROUP",name = "执行组")
    private String execGroup;

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
