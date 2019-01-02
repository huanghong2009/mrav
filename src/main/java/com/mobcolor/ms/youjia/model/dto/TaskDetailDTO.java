package com.mobcolor.ms.youjia.model.dto;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.ms.youjia.enums.CountType;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2017-12-26 14:29:07
 */
@Data
public class TaskDetailDTO extends BaseDTO {
        
    /**
     *
     */
    private String taskForcetId;
    
        
    /**
     *
     */
    private String taskListId;
    
        
    /**
     *
     */
    private String name;
    
        
    /**
     *
     */
    private String serverConfig;
    
        
    /**
     *
     */
    private TaskList type;
    
        
    /**
     *
     */
    private TaskDetail state;

    /**
     * 是否收到回调
     */
    private String isCallBcak;
    
        
    /**
     *
     */
    private String scriptNames;
    
        
    /**
     *
     */
    private String device;
    
        
    /**
     *
     */
    private JSONObject otherInfo;
    
        
    /**
     *
     */
    private String createrTime;

    private CountType countType;

    private String startTime;

    private String endTime;
        
    /**
     *
     */
    private String extendField1;
    
        
    /**
     *
     */
    private String extendField2;

}
    
