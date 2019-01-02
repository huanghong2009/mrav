package com.mobcolor.ms.youjia.enums;

public enum Account {
    INVALID("失效"),
    VALID("有效");

    private String desc;

    private Account(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
