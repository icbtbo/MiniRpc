package com.mini.rpc.core;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc服务帮助类
 * @date 2022/6/17 8:37 下午
 */
public class RpcServiceHelper {

    /**
     * 根据 服务名 和 服务版本 构造一个 String 类型的 key
     * @param serviceName 服务名(服务接口名)
     * @param serviceVersion 服务版本
     * @return: java.lang.String
    */
    public static String buildServiceKey(String serviceName, String serviceVersion){
        return String.join("#", serviceName, serviceVersion);
    }

}
