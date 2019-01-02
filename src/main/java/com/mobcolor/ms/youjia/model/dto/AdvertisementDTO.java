package com.mobcolor.ms.youjia.model.dto;


import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-01-05 14:10:27
 */
@Data
public class AdvertisementDTO extends BaseDTO {
        
    /**
     *
     */
    private String originalAdvertisementId;
    
        
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
    private String scriptNames;
    
        
    /**
     *
     */
    private String taskLists;
    
        
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
    
