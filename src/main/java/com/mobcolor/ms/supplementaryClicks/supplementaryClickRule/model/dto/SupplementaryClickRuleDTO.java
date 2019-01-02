package com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.model.dto;


import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-01-23 21:04:43
 */
@Data
public class SupplementaryClickRuleDTO extends BaseDTO {
        
    /**
     *
     */
    private String initMethod;
    
        
    /**
     *
     */
    private String callBackMethod;
    
        
    /**
     *
     */
    private String changeStateMethod;
    
        
    /**
     *
     */
    private Date createrTime;
    
        
    /**
     *
     */
    private Date updateTime;
    
        
    /**
     *
     */
    private String extendField1;
    
        
    /**
     *
     */
    private String extendField2;
    
    }
    
