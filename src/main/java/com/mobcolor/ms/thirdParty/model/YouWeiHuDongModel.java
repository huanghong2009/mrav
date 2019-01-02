package com.mobcolor.ms.thirdParty.model;


import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
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
@ServerModel("OTHER_YOUWEIHUDONG")
public class YouWeiHuDongModel extends BaseModel {

    /**
     * 有位互动分配给己方的ID
     */
    @ServerField(value = "CID",name = "有位互动分配给己方的ID")
    private String cid;

    /**
     * 应用名称
     */
    @ServerField(value = "APP_NAME",name = "应用名称")
    private String appName;

    /**
     * 应用id
     */
    @ServerField(value = "APP_ID",name ="应用id" )
    private String appId;


    /**
     * 应用渠道id
     */
    @ServerField(value = "PLATFORM_CHANNEL_ID",name = "应用渠道id")
    private String platformChannelId;

    /**
     * 应用渠道名称
     */
    @ServerField(value = "PLATFORM_CHANNEL_NAME",name = "应用渠道名称")
    private String platformChannelName;

    /**
     * idfa
     */
    @ServerField(value = "IDFA",name = "idfa")
    private String idfa;

    /**
     *ip
     */
    @ServerField(value = "IP",name = "ip")
    private String ip;

    /**
     *用户搜索的关键词 UTF-8 - urlencode编码
     */
    @ServerField(value = "WORD",name = "用户搜索的关键词 UTF-8 - urlencode编码")
    private String word;

    /**
     *IOS系统版本号
     */
    @ServerField(value = "SYS_VER",name = "IOS系统版本号")
    private String sysVer;

    /**
     * 任务类型
     */
    @ServerField(value = "TYPE",name = "任务类型")
    private TaskList type;

    /**
     * 任务状态
     */
    @ServerField(value = "STATE",name = "任务状态")
    private TaskDetail state;

    /**
     * 设备
     */
    @ServerField(value = "DEVICE",name = "设备")
    private String device;

    /**
     * 其他信息
     */
    @ServerField(value = "OTHER_INFO",name = "其他信息")
    private String otherInfo;


    /**
     * 是否收到回调
     */
    @ServerField(value = "IS_CALL_BACK",name = "是否收到回调")
    private String isCallBcak;

    /**
     * 回调的返回数据
     */
    @ServerField(value = "CALL_BACK_DATA",name = "回调的返回数据")
    private String callBackData;

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
