package com.mobcolor.ms.youjia.enums;

public enum Script {
    MAIN("主脚本"),
    COMMON("公共脚本"),
    EXECUTION("执行脚本"),

    EXISTING("存在"),
    DELETE("删除了");

    private String name;

    private Script(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
