package com.mobcolor.ms.user.enums;

/**
 * @author 黄鸿 E-mail：hong.huang@jsxfedu.com
 * @version 1.0 创建时间： 2017/3/23.
 */
public enum CrmEnmu {

    ENABLE("启用"),

    DISABLE("停用");

    private CrmEnmu(String description){
        this.description = description;
    }

    private String description;

    public String getDescription(){
        return description;
    }


}
