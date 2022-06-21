package com.mini.rpc.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description spring boot 启动类
 * @date 2022/6/17 12:09 下午
 */
@SpringBootApplication
public class RpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcProviderApplication.class, args);
    }
}
