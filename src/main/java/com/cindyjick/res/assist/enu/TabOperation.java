package com.cindyjick.res.assist.enu;

public enum TabOperation {
    ADD("add", "新增"),
    REMOVE("remove", "关闭"),
    REMOVE_LEFT("removeLeft", "关闭左边"),
    REMOVE_RIGHT("removeRight", "关闭右边"),
    REMOVE_OTHER("removeOther", "关闭其它"),
    CLEAN("clean", "关闭所有"),
    ;

    TabOperation(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
