package com.mini.rpc.protocol;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 消息类型 枚举类
 * @date 2022/6/18 8:07 下午
 */
public enum MsgType {
    /** 请求消息 */
    REQUEST(1),
    /** 响应消息 */
    RESPONSE(2),
    /** 心跳消息 */
    HEARTBEAT(3);

    private final int type;

    MsgType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static MsgType findByType(int type){
        for (MsgType msgType : MsgType.values()) {
            if(msgType.getType() == type){
                return msgType;
            }
        }
        return null;
    }
}
