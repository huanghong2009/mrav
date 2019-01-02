package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.ms.youjia.enums.Script;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2017-12-26 12:24:43
 */
@Data
public class ScriptDTO extends BaseDTO {
        
    private String name;
    
        
    private String platform;
    
        
    private Script type;
    
        
    private String content;
    
        
    private String remarks;
    
        
    private String state;
    
        
    private Date createrTime;
    
        
    private String extendField1;
    
        
    private String extendField2;
    
}
    
