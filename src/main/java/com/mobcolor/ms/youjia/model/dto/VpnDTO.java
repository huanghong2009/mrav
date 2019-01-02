package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.framework.common.BaseDTO;
import com.mobcolor.ms.youjia.enums.Account;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-04-12 15:12:35
 */
@Data
public class VpnDTO extends BaseDTO{

    private String type;
        
    /**
     *
     */
    private String account;
    
        
    /**
     *
     */
    private String passWord;
    
        
    /**
     *
     */
    private String region;
    
        
    /**
     *
     */
    private Account state;

    private String isNullDevice;
    
        
    /**
     *
     */
    private Date lastUpdateTime;
    
        
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
    
