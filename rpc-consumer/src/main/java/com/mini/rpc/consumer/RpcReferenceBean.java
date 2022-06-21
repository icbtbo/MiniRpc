package com.mini.rpc.consumer;

import com.mini.rpc.registry.RegistryFactory;
import com.mini.rpc.registry.RegistryService;
import com.mini.rpc.registry.RegistryType;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description 用于生成 Rpc代理类 的工厂bean
 * @date 2022/6/18 4:35 下午
 */
public class RpcReferenceBean implements FactoryBean<Object> {
    /** 该工厂bean生成的bean类型 */
    private Class<?> interfaceClass;
    /** 服务版本 */
    private String serviceVersion;
    /** 注册中心类型 */
    private String registryType;
    /** 注册中心地址 */
    private String registryAddr;
    /** 超时时间 */
    private long timeout;
    /** 生成的代理对象 */
    private Object object;

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public void init() throws Exception {
        // 生成动态代理
        RegistryService registryService = RegistryFactory.getInstance(this.registryAddr, RegistryType.valueOf(this.registryType));
        this.object = Proxy.newProxyInstance(this.interfaceClass.getClassLoader(),
                                             new Class<?>[]{this.interfaceClass},
                                             new RpcInvokerProxy(serviceVersion, timeout, registryService)
        );

    }


    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public void setRegistryAddr(String registryAddr) {
        this.registryAddr = registryAddr;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
