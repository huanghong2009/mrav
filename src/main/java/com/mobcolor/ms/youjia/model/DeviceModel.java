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
@ServerModel("DEVICE")
public class DeviceModel extends BaseModel {

    /**
     * 设备组id
     */
    @ServerField(value = "DEVICE_GROUP",name = "设备组id")
    private String deviceGroupId;

    /**
     * 名称
     */
    @ServerField(value = "NAME",name = "名称")
    private String name;

    /**
     * 设备编号
     */
    @ServerField(value = "DEVICE_NUMBER",name = "设备编号")
    private String deviceNumber;

    /**
     * 触动编号
     */
    @ServerField(value = "TS_NUMBER",name = "触动编号")
    private String tsNumber;

    /**
     * 设备型号
     */
    @ServerField(value = "DEVICE_MODEL",name = "设置型号")
    private String devcieModel;

    /**
     * 系统版本号
     */
    @ServerField(value = "SYSTEM_VERSION",name = "系统版本号")
    private String systemVersion;


    /**
     * 其他信息
     */
    @ServerField(value = "OTHER_INFO",name = "其他信息")
    private String otherInfo;

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
