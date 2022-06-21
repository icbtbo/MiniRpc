package com.mini.rpc.protocol;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description TODO
 * @date 2022/6/18 10:07 下午
 */
public class ProtocolConstants {
    /** 协议头大小（单位：byte） */
    public static final int HEADER_TOTAL_LEN = 18;
    /** 协议的魔数 */
    public static final short MAGIC = 0x10;
    /** 协议版本 */
    public static final byte VERSION = 0x01;

}
