package com.mini.rpc.provider.annotion;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc服务注解，用于暴露并发布服务
 * @date 2022/6/17 5:05 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {

    /** 服务接口 */
    Class<?> serviceInterface() default Object.class;
    /** 服务版本 */
    String serviceVersion() default "1.0";

}
