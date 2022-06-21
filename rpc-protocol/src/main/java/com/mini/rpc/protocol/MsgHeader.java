package com.mini.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 协议头
 * @date 2022/6/18 7:56 下午
 */
@Data
public class MsgHeader implements Serializable {

    /** 魔数 */
    private short magic;
    /** 协议版本号 */
    private byte version;
    /** 序列化算法 */
    private byte serialization;
    /** 消息类型 */
    private byte msgType;
    /** 状态 */
    private byte status;
    /** 消息ID */
    private long requestId;
    /** 数据长度 */
    private int dataLen;

}
