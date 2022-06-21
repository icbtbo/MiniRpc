package com.mini.rpc.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc Server 参数配置类
 * @date 2022/6/17 3:15 下午
 */
@Data
@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {
    /** 服务端口 */
    private int servicePort;
    /** 注册中心地址 */
    private String registryAddr;
    /** 注册中心类型 */
    private String registryType;

}
