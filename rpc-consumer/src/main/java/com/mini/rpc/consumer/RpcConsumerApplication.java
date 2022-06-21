package com.mini.rpc.consumer;

import com.mini.rpc.core.RpcProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description rpc-consumer 启动类
 * @date 2022/6/18 4:34 下午
 */
@SpringBootApplication
@EnableConfigurationProperties(RpcProperties.class)
public class RpcConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerApplication.class, args);
    }
}