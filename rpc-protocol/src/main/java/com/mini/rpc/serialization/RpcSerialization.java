package com.mini.rpc.serialization;

import java.io.IOException;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc 序列化接口
 * @date 2022/6/18 8:26 下午
 */
public interface RpcSerialization {

    /**
     * 将 obj 对象序列化为二进制数据
     * @param obj 要序列化的对象
     * @return: byte[]
    */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * 将二进制数据反序列化为 clazz 类型的对象
     * @param data 二进制对象
     * @param clazz 目标类型
     * @return: T
    */
    <T> T deserialize(byte[] data, Class<T> clazz) throws IOException;

}
