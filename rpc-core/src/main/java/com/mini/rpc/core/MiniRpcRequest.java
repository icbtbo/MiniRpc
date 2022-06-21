package com.mini.rpc.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc请求 协议体
 * @date 2022/6/19 2:25 下午
 */
@Data
public class MiniRpcRequest implements Serializable {

    /** 服务版本 */
    private String serviceVersion;
    /** 服务接口名 */
    private String className;
    /** 服务方法名 */
    private String methodName;
    /** 方法参数列表 */
    private Object[] params;
    /** 方法参数类型列表 */
    private Class<?>[] parameterTypes;

}
