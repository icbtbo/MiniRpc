package com.mini.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc 通信协议
 * +---------------------------------------------------------------+
 *
 * | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
 *
 * +---------------------------------------------------------------+
 *
 * | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
 *
 * +---------------------------------------------------------------+
 *
 * |                   数据内容 （长度不定）                          |
 *
 * +---------------------------------------------------------------+
 * @date 2022/6/18 7:49 下午
 */

@Data
public class MiniRpcProtocol<T> implements Serializable {
    /** 协议头 */
    private MsgHeader header;
    /** 协议体 */
    private T body;

}
