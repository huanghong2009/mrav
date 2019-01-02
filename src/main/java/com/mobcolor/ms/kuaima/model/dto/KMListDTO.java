package com.mobcolor.ms.kuaima.model.dto;

import com.mobcolor.framework.common.BaseDTO;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-08-01 14:54:40
 */
@Data
public class KMListDTO extends BaseDTO{
        
    /**
     *
     */
    private String taskId;
    
        
    /**
     *
     */
    private String name;
    
        
    /**
     *
     */
    private Date startTime;
    
        
    /**
     *
     */
    private Date endTime;
    
        
    /**
     *
     */
    private Long total;
    
        
    /**
     *
     */
    private Long succeedNumber;
    
        
    /**
     *
     */
    private Long failedNumber;
    
        
    /**
     *
     */
    private TaskDetail state;
    
        
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

    /**
     *
     */
    private String execTime;


    /**
     * 是否只查询今天 y/n
     */
    private String isQueryToDay;

    private String sortName;

    private String sortOrder;

}
    
