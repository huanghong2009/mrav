package com.mobcolor.ms.kuaima.model.dto;

import com.mobcolor.framework.common.BaseDTO;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-08-01 14:55:02
 */
@Data
public class KMDTO extends BaseDTO{
        
    /**
     *
     */
    private String name;
    
        
    /**
     *
     */
    private String scriptNames;
    
        
    /**
     *
     */
    private Long taskTotal;
    
        
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
    private String config;
    
        
    /**
     *
     */
    private String execTime;
    
        
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
     * 是否只查询今天 y/n
     */
    private String isQueryToDay;
    
}
    
