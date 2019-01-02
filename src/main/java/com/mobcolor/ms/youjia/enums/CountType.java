package com.mobcolor.ms.youjia.enums;

public enum CountType {
    DAY("今天"),
    WEEK("本周"),
    MONTH("本月"),
    CUSTOM("自定义时间段");

    private String desc;

    private CountType(String name){
        this.desc = name;
    }

    public String getName() {
        return desc;
    }

    public void setName(String name) {
        this.desc = name;
    }
}
