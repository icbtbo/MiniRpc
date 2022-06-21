package com.mini.rpc.consumer.annotation;


import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc代理注解，用于生成代理，完成对方法的拦截，进行rpc调用。
 * @date 2022/6/18 4:20 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface RpcReference {
    /** 服务版本 */
    String serviceVersion() default "1.0";
    /** 注册中心类型 */
    String registryType() default "ZOOKEEPER";
    /** 注册中心地址 */
    String registryAddress() default "127.0.0.1:2181";
    /** 超时时间 */
    long timeout() default 5000;

}
