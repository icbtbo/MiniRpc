package com.mini.rpc.registry;

import com.mini.rpc.core.ServiceMeta;

import java.io.IOException;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 注册中心接口
 * @date 2022/6/17 3:43 下午
 */
public interface RegistryService {
    /**
     * 服务注册
    */
    void register(ServiceMeta serviceMeta) throws Exception;
    /**
     * 服务注销
     */
    void unRegister(ServiceMeta serviceMeta) throws Exception;
    /**
     * 服务发现
     */
    ServiceMeta discovery(String serviceName, int invokerHashCode) throws Exception;
    /**
     * 销毁注册中心实例
     */
    void destroy() throws IOException;
}
