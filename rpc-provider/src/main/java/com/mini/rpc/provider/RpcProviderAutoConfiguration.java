package com.mini.rpc.provider;

import com.mini.rpc.core.RpcProperties;
import com.mini.rpc.registry.RegistryFactory;
import com.mini.rpc.registry.RegistryService;
import com.mini.rpc.registry.RegistryType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 配置类，将 Rpc服务类 注册到容器中，从而在实例化的时候启动 Rpc服务
 * @date 2022/6/17 3:29 下午
 */
@Configuration
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProviderAutoConfiguration {

    @Resource
    RpcProperties rpcProperties;

    @Bean
    public RpcProvider init() throws Exception {
        RegistryType type = RegistryType.valueOf(rpcProperties.getRegistryType());
        RegistryService serviceRegistry = RegistryFactory.getInstance(rpcProperties.getRegistryAddr(), type);

        return new RpcProvider(rpcProperties.getServicePort(), serviceRegistry);
    }
}
