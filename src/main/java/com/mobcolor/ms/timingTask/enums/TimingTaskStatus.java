package com.mobcolor.ms.timingTask.enums;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/9/27
 */
public enum TimingTaskStatus {
    EXECUTION_WAIT("等待执行"),
    EXECUTION_RUN("执行中"),
    EXECUTION_CANCEL("任务取消"),
    EXECUTION_SUCCEED("执行成功"),
    EXECUTION_CYCLE("任务循环"),
    EXECUTION_FAILED("执行失败");

    private String desc;

    private TimingTaskStatus(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
