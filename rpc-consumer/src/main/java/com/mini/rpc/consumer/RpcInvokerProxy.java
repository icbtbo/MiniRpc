package com.mini.rpc.consumer;

import com.mini.rpc.core.MiniRpcFuture;
import com.mini.rpc.core.MiniRpcRequest;
import com.mini.rpc.core.MiniRpcRequestHolder;
import com.mini.rpc.core.MiniRpcResponse;
import com.mini.rpc.protocol.MiniRpcProtocol;
import com.mini.rpc.protocol.MsgHeader;
import com.mini.rpc.protocol.MsgType;
import com.mini.rpc.protocol.ProtocolConstants;
import com.mini.rpc.registry.RegistryService;
import com.mini.rpc.serialization.RpcSerialization;
import com.mini.rpc.serialization.SerializationFactory;
import com.mini.rpc.serialization.SerializationTypeEnum;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc调用处理器
 * @date 2022/6/21 11:53 上午
 */
public class RpcInvokerProxy implements InvocationHandler {

    private final String serviceVersion;

    private final long timeout;

    private final RegistryService registryService;

    public RpcInvokerProxy(String serviceVersion, long timeout, RegistryService registryService) {
        this.serviceVersion = serviceVersion;
        this.timeout = timeout;
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 封装 Rpc协议 对象 (包含rpc请求消息)
        MiniRpcProtocol<MiniRpcRequest> rpcProtocol = new MiniRpcProtocol<>();
        // 协议头
        MsgHeader header = new MsgHeader();
        long requestId = MiniRpcRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        header.setMagic(ProtocolConstants.MAGIC);
        header.setVersion(ProtocolConstants.VERSION);
        header.setRequestId(requestId);
        header.setSerialization((byte) SerializationTypeEnum.HESSIAN.getType());
        header.setMsgType((byte) MsgType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        rpcProtocol.setHeader(header);
        // 协议体
        MiniRpcRequest request = new MiniRpcRequest();
        request.setServiceVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        rpcProtocol.setBody(request);

        // 设置消息长度
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
        try {
            header.setDataLen(rpcSerialization.serialize(request).length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 维护 requestId 和 MiniRpcFuture 之间的映射关系
        MiniRpcFuture<MiniRpcResponse> miniRpcFuture = new MiniRpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()), this.timeout);
        MiniRpcRequestHolder.REQUEST_MAP.put(requestId, miniRpcFuture);

        // 发起 Rpc 远程调用
        RpcConsumer rpcConsumer = new RpcConsumer();
        rpcConsumer.sendRequest(rpcProtocol, this.registryService);

        // 等待 Rpc 调用响应结果
        return miniRpcFuture.getPromise().get(miniRpcFuture.getTimeout(), TimeUnit.MILLISECONDS).getData();
    }
}
