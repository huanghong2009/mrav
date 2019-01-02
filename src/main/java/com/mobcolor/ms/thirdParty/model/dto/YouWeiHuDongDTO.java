package com.mobcolor.ms.thirdParty.model.dto;


import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-01-18 19:13:44
 */
@Data
public class YouWeiHuDongDTO extends BaseDTO {
        
    /**
     *
     */
    private String cid;
    
        
    /**
     *
     */
    private String appName;
    
        
    /**
     *
     */
    private String appId;
    
        
    /**
     *
     */
    private String platformChannelId;
    
        
    /**
     *
     */
    private String platformChannelName;
    
        
    /**
     *
     */
    private String idfa;
    
        
    /**
     *
     */
    private String ip;
    
        
    /**
     *
     */
    private String word;
    
        
    /**
     *
     */
    private String sysVer;
    
        
    /**
     *
     */
    private TaskList type;
    
        
    /**
     *
     */
    private TaskDetail state;
    
        
    /**
     *
     */
    private String isCallBcak;
    
        
    /**
     *
     */
    private String callBackData;
    
        
    /**
     *
     */
    private String createrTime;
    
        
    /**
     *
     */
    private String updateTime;
    
        
    /**
     *
     */
    private String extendField1;
    
        
    /**
     *
     */
    private String extendField2;
    
    }
    
