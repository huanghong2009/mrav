package com.mobcolor.ms.youjia.enums;

public enum PatchClicksType {
    ADVERTISER_CALL_BACK("激活回调"),
    DEVICE_CALL_BACK("设备回调");

    private String name;

    private PatchClicksType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
