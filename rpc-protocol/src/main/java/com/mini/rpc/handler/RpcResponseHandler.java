package com.mini.rpc.handler;

import com.mini.rpc.core.MiniRpcFuture;
import com.mini.rpc.core.MiniRpcRequestHolder;
import com.mini.rpc.core.MiniRpcResponse;
import com.mini.rpc.protocol.MiniRpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc响应 处理器
 * @date 2022/6/20 1:46 下午
 */
@Slf4j
public class RpcResponseHandler extends SimpleChannelInboundHandler<MiniRpcProtocol<MiniRpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MiniRpcProtocol<MiniRpcResponse> msg) throws Exception {
        // 根据 requestId 获取 MiniRpcFuture，然后通过其中的 promise 设置响应结果
        long requestId = msg.getHeader().getRequestId();
        MiniRpcFuture<MiniRpcResponse> miniRpcFuture = MiniRpcRequestHolder.REQUEST_MAP.get(requestId);
        log.info(">>> response is " + msg.getBody().getData());
        miniRpcFuture.getPromise().setSuccess(msg.getBody());
    }
}
