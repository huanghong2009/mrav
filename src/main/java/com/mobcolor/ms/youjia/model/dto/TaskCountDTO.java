package com.mobcolor.ms.youjia.model.dto;

import com.alibaba.fastjson.JSONObject;
import com.mobcolor.framework.common.BaseDTO;
import com.mobcolor.ms.youjia.enums.TaskDetail;
import com.mobcolor.ms.youjia.enums.TaskList;
import lombok.Data;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2017-12-26 14:29:07
 */
@Data
public class TaskCountDTO extends BaseDTO {
        
   private Integer num;

   private Integer callBackNum;

   private String filed;
    
        
    /**
     *
     */
    private String extendField1;
    
        
    /**
     *
     */
    private String extendField2;
    
}
    
