package com.mobcolor.ms.youjia.model.dto;

import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/21
 */
@Data
public class PlatformChannelDTO extends BaseDTO {

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 系统平台
     */
    private String platform;
}
