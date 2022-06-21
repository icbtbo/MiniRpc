package com.mini.rpc.registry;

import com.mini.rpc.core.ServiceMeta;
import com.mini.rpc.registry.RegistryService;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Eureka 注册中心
 * @date 2022/6/20 10:39 下午
 */
public class EurekaRegistryService implements RegistryService {

    public EurekaRegistryService(String registryAddr) {
        // TODO
    }

    @Override
    public void register(ServiceMeta serviceMeta) {

    }

    @Override
    public void unRegister(ServiceMeta serviceMeta) {

    }

    @Override
    public ServiceMeta discovery(String serviceName, int invokerHashCode) {
        return null;
    }

    @Override
    public void destroy() {

    }
}
