package com.mobcolor.ms.youjia.model;

import com.mobcolor.framework.common.BaseModel;
import lombok.Data;

import java.util.Date;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2018/3/5
 */
@Data
public class AccountErrorLogModel extends BaseModel {

    /**
     * 表名
     */
    public static String tableName = "marv_account_error_log";

    private String batchNo;

    /**
     * meid
     */
    private String account;

    /**
     * log
     */
    private String log;

    /**
     * 创建时间
     */
    private Date createrTime;

}
