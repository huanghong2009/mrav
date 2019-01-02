package com.mobcolor.ms.youjia.enums;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/26
 */
public enum TaskList {
    RETAINED("留存"),
    ADD("新增");

    private String desc;

    private TaskList(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
