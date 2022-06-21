package com.mini.rpc.consumer.controller;

import com.mini.rpc.consumer.annotation.RpcReference;
import com.mini.rpc.core.RpcProperties;
import com.mini.rpc.facade.HelloFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author songjiancheng
 * @version 1.0
 * @Description 用于测试的 api 接口
 * @date 2022/6/21 3:18 下午
 */
@RestController
public class HelloController {


    @SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpringJavaInjectionPointsAutowiringInspection"})
    @RpcReference(serviceVersion = "1.0", timeout = 3000, registryAddress = "127.0.0.1:2181")
    HelloFacade helloFacade;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello(){
        return helloFacade.sayHello("mini rpc");
    }

}
