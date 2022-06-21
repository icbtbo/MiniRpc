package com.mini.rpc.registry;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 注册中心工厂类
 * @date 2022/6/17 3:43 下午
 */
public class RegistryFactory {

    private static volatile RegistryService registryService;

    public static RegistryService getInstance(String registryAddr, RegistryType type) throws Exception {
        // double-check singleton
        if (null == registryService) {
            synchronized (RegistryFactory.class) {
                if (null == registryService) {
                    switch (type) {
                        case ZOOKEEPER:
                            registryService = new ZookeeperRegistryService(registryAddr);
                            break;
                        case EUREKA:
                            registryService = new EurekaRegistryService(registryAddr);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return registryService;
    }

}
