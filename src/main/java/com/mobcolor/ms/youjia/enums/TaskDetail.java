package com.mobcolor.ms.youjia.enums;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/26
 */
public enum TaskDetail {
    WAIT("等待"),
    RUNNING("运行中"),
    SUCCEED("成功"),
    FAILED("失败"),
    SUSPEND("暂停");

    private String desc;

    private TaskDetail(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
