package com.mini.rpc.protocol;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 消息状态 枚举类
 * @date 2022/6/18 8:06 下午
 */
public enum MsgStatue {
    /** 成功 */
    SUCCESS(1),
    /** 失败 */
    FAIL(2);

    private final int code;

    MsgStatue(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
