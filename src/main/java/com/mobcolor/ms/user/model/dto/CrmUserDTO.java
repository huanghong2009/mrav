package com.mobcolor.ms.user.model.dto;

import com.mobcolor.ms.user.enums.CrmEnmu;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

/**
 * @author 黄鸿 E-mail：hong.huang@jsxfedu.com
 * @version 1.0 创建时间： 2016/9/29.
 */
@Data
public class CrmUserDTO extends BaseDTO {

    private String email;

    private CrmEnmu status;

    private String editor_id;
}
