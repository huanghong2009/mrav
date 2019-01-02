package com.mobcolor.ms.supplementaryClicks.cake.model.dto;


import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-01-24 10:45:41
 */
@Data
public class CakeSupplementaryClickDTO extends BaseDTO {
        
    /**
     *
     */
    private String originalAdvertisementId;


    private String taskListId;
        
    /**
     *
     */
    private int scheduleStart;


    private SupplementaryClick state;
        
    /**
     *
     */
    private int scheduleEnd;


    /**
     *
     */
    private String execTime;
    
        
    /**
     *
     */
    private String clickUrl;
    
        
    /**
     *
     */
    private Date createrTime;


    /**
     * 是否溢出 y n
     */
    private String overflow;
        
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
    
