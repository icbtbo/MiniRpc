package com.mini.rpc.handler;

import com.mini.rpc.core.MiniRpcRequest;
import com.mini.rpc.core.MiniRpcResponse;
import com.mini.rpc.core.RpcServiceHelper;
import com.mini.rpc.protocol.MiniRpcProtocol;
import com.mini.rpc.protocol.MsgHeader;
import com.mini.rpc.protocol.MsgStatue;
import com.mini.rpc.protocol.MsgType;
import com.mini.rpc.serialization.RpcSerialization;
import com.mini.rpc.serialization.SerializationFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc请求 处理器
 * @date 2022/6/20 1:46 下午
 */
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<MiniRpcProtocol<MiniRpcRequest>> {
    /** 存着已注册的服务:serviceKey->bean */
    private final Map<String, Object> rpcServiceMap;

    public RpcRequestHandler(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MiniRpcProtocol<MiniRpcRequest> msg) throws Exception {
        log.info(">>> start handle request");

        RpcRequestProcessor.submitReqHandlerTask(()->{
            // 调用请求的方法，封装 MiniRpcProtocol<MiniRpcResponse> 并返回

            MiniRpcProtocol<MiniRpcResponse> miniRpcProtocol = new MiniRpcProtocol<>();
            MsgHeader header = msg.getHeader();
            // 修改消息类型
            header.setMsgType((byte) MsgType.RESPONSE.getType());
            MiniRpcResponse rpcResponse = new MiniRpcResponse();
            try {
                // 调用请求的方法
                Object result = handle(msg.getBody());
                rpcResponse.setData(result);
                log.info(">>> return value is " + result);

                // 设置响应状态
                header.setStatus((byte) MsgStatue.SUCCESS.getCode());
                // 设置消息长度
                RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
                try {
                    header.setDataLen(rpcSerialization.serialize(rpcResponse).length);
                } catch (Throwable e) {
                    log.error("failed to serialize response");
                }
                // 封装 protocol
                miniRpcProtocol.setHeader(header);
                miniRpcProtocol.setBody(rpcResponse);

            } catch (Throwable throwable) {
                // 设置错误信息
                rpcResponse.setMessage(throwable.toString());

                // 设置响应状态
                header.setStatus((byte) MsgStatue.FAIL.getCode());
                // 设置消息长度
                RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
                try {
                    header.setDataLen(rpcSerialization.serialize(rpcResponse).length);
                } catch (Throwable e) {
                    log.error("failed to serialize response");
                }

                // 封装 protocol
                miniRpcProtocol.setHeader(header);
                miniRpcProtocol.setBody(rpcResponse);

                log.error("process request {} failed", header.getRequestId(), throwable);
            }

            // 将响应结果写回客户端
            ctx.writeAndFlush(miniRpcProtocol);
            log.info(">>> response has been write back");
        });
    }

    private Object handle(MiniRpcRequest rpcRequest) throws InvocationTargetException {
        // 获取 rpc调用的方法所对应的 bean
        String serviceKey = RpcServiceHelper.buildServiceKey(rpcRequest.getClassName(), rpcRequest.getServiceVersion());
        Object serviceBean = rpcServiceMap.get(serviceKey);

        // 从 rpcRequest 中提取方法名、参数类型列表和参数列表
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] params = rpcRequest.getParams();

        // 采用 Cglib 提供的 FastClass 机制直接调用方法
        Class<?> clz = serviceBean.getClass();
        FastClass fastClass = FastClass.create(clz);
        int methodIndex = fastClass.getIndex(methodName, parameterTypes);
        return fastClass.invoke(methodIndex, serviceBean, params);

    }
}
