package com.mobcolor.ms.youjia.enums;


/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/19
 */
public enum Platform {
    ANDROID("安卓"),
    IOS("苹果");


    private String name;

    private Platform(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

