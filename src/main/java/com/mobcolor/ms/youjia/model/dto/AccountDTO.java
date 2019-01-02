package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.framework.common.BaseDTO;
import com.mobcolor.ms.youjia.enums.Account;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-04-09 15:23:00
 */
@Data
public class AccountDTO extends BaseDTO{

    
        
    /**
     *
     */
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
    private Account state;
    
        
    /**
     *
     */
    private Date lastUpdateTime;
    
        
    /**
     *
     */
    private String extendField1;
    
        
    /**
     *
     */
    private String extendField2;
    
    }
    
