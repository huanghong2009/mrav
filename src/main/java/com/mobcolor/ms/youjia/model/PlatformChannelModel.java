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
@ServerModel("PLATFORM")
public class PlatformChannelModel extends BaseModel {
    /**
     * 渠道名称
     */
    @ServerField(value = "NAME")
    private String name;

    /**
     * 系统平台
     */
    @ServerField(value = "PLATFORM")
    private String platform;

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
