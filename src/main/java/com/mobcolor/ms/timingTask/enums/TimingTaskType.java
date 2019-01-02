package com.mobcolor.ms.timingTask.enums;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
public enum TimingTaskType {
    DIRECT_EXECUTION("立即执行"),
    TIMEING_EXECUTION("定时执行"),
    TIMER_EXECUTION("定时器执行");

    private String desc;

    private TimingTaskType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
