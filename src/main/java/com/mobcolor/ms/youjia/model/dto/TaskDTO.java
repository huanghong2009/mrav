package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.framework.common.BaseDTO;
import com.mobcolor.ms.youjia.enums.PatchClicksType;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2017-12-26 13:20:22
 */
@Data
public class TaskDTO extends BaseDTO {

    /**
     *执行次数
     */
    private Integer num;

    /**
     * 是否需要回调
     */
    private String isNeedCallBack;

    /**
     * 开始执行时间
     */
    private String startTime;

    /**
     * 广告id
     */
    private String advertisementId;

    private PatchClicksType patchClicksType;
    
}
    
