package com.mini.rpc.codec;

import com.mini.rpc.protocol.MiniRpcProtocol;
import com.mini.rpc.protocol.MsgHeader;
import com.mini.rpc.serialization.RpcSerialization;
import com.mini.rpc.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc协议 编码器
 * @date 2022/6/18 9:53 下午
 */
@Slf4j
public class MiniRpcEncoder extends MessageToByteEncoder<MiniRpcProtocol<Object>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MiniRpcProtocol<Object> msg, ByteBuf out) throws Exception {
        MsgHeader header = msg.getHeader();
        // 编码 协议头
        out.writeShort(header.getMagic());
        out.writeByte(header.getVersion());
        out.writeByte(header.getSerialization());
        out.writeByte(header.getMsgType());
        out.writeByte(header.getStatus());
        out.writeLong(header.getRequestId());
        out.writeInt(header.getDataLen());

        // 编码 协议体
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(header.getSerialization());
        // -- 序列化 协议体
        byte[] data = rpcSerialization.serialize(msg.getBody());
        out.writeBytes(data);
        log.info(">>> protocol has been encoded");
    }

}
