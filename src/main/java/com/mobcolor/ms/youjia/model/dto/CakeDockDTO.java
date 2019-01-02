package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-01-11 14:47:10
 */
@Data
public class CakeDockDTO extends BaseDTO {

    private String id;

    /**
     *
     */
    private String taskDetailId;
    
        
    /**
     *
     */
    private String state;
    
        
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
    
    }
    
