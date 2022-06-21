package com.mini.rpc.provider.facade;

import com.mini.rpc.facade.HelloFacade;
import com.mini.rpc.provider.annotion.RpcService;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description HelloFacade 的实现类
 * @date 2022/6/21 3:35 下午
 */
@RpcService(serviceInterface = HelloFacade.class, serviceVersion = "1.0")
public class HelloFacadeImpl implements HelloFacade {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
