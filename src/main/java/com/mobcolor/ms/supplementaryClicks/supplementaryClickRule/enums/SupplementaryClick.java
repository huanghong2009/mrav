package com.mobcolor.ms.supplementaryClicks.supplementaryClickRule.enums;

/**
 * @author huanghong E-mail:767980702@qq.com
 * @version 创建时间：2017/12/26
 */
public enum SupplementaryClick {
    RUNNING("运行中"),
    SUSPEND("暂停");

    private String desc;

    private SupplementaryClick(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
