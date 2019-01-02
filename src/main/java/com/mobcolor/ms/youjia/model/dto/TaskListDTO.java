package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.ms.youjia.enums.TaskList;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2017-12-26 13:51:21
 */
@Data
public class TaskListDTO extends BaseDTO {

    /**
     *
     */
    private String taskForcetId;

    private String name;
    
        
    /**
     *
     */
    private TaskList type;
    
        
    /**
     *
     */
    private String num;
    
        
    /**
     *
     */
    private String taskSchedule;
    
        
    /**
     *
     */
    private String execTime;

    /**
     *
     */
    private String isCreate;

    /**
     *
     */
    private String repeatedlyCorrelationId;


    /**
     *
     */
    private String isRepeatedly;
    
        
    /**
     *
     */
    private Date createrTime;
    
        
    /**
     *
     */
    private String extendField1;
    
        
    /**
     *
     */
    private String extendField2;


    private String isQueryAll;

    private String isQueryHaving;
}
    
