package com.mobcolor.ms.supplementaryClicks.cake.model.dto;


import com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums.SupplementaryClick;
import com.mobcolor.framework.common.BaseDTO;
import lombok.Data;

import java.util.Date;


/**
 *
 * @author huanghong 邮箱:767980702@qq.com
 * @version 1.0 创建时间 : 2018-01-24 10:45:41
 */
@Data
public class CakeSupplementaryClickBusinessDTO extends BaseDTO {
        private String analog_click;

        private String region;

        private String key;

        private Date time;

        private String click_url;

        private String id;
    
}
    
