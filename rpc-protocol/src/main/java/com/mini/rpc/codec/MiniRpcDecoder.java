package com.mini.rpc.codec;

import com.mini.rpc.core.MiniRpcRequest;
import com.mini.rpc.core.MiniRpcResponse;
import com.mini.rpc.protocol.MiniRpcProtocol;
import com.mini.rpc.protocol.MsgHeader;
import com.mini.rpc.protocol.MsgType;
import com.mini.rpc.protocol.ProtocolConstants;
import com.mini.rpc.serialization.RpcSerialization;
import com.mini.rpc.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;


/**
 * @author songjiancheng
 * @version 1.0
 * @Description Rpc协议 解码器
 * @date 2022/6/18 9:54 下午
 */
@Slf4j
public class MiniRpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 如果可读字节数小于 协议头 的大小，则直接返回
        if(in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN){
            return;
        }

        in.markReaderIndex();
        short magic = in.readShort();
        if(magic != ProtocolConstants.MAGIC){
            throw new IllegalArgumentException("magic number [" + magic + "] is illegal");
        }
        byte version = in.readByte();
        byte serializationType = in.readByte();
        byte msgType = in.readByte();
        if(null == MsgType.findByType(msgType)){
            throw new IllegalArgumentException("msgType number [" + msgType + "] is illegal");
        }
        byte status = in.readByte();
        long requestId = in.readLong();
        int dataLen = in.readInt();
        if(in.readableBytes() < dataLen){
            log.info(">>> there is not enough byte!");
            in.resetReaderIndex();
            return;
        }
        // 从 byteBuf 中读取协议体
        byte[] data = new byte[dataLen];
        in.readBytes(data);

        // 封装 MiniRpcProtocol 对象

        // -- 封装 协议头
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setMagic(magic);
        msgHeader.setVersion(version);
        msgHeader.setSerialization(serializationType);
        msgHeader.setMsgType(msgType);
        msgHeader.setStatus(status);
        msgHeader.setRequestId(requestId);
        msgHeader.setDataLen(dataLen);

        // -- 封装协议体
        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(serializationType);
        switch (Objects.requireNonNull(MsgType.findByType(msgType))){
            case REQUEST:
                log.info(">>> decode request");
                MiniRpcRequest rpcRequest = rpcSerialization.deserialize(data, MiniRpcRequest.class);
                if(null != rpcRequest){
                    MiniRpcProtocol<MiniRpcRequest> miniRpcProtocol = new MiniRpcProtocol<>();
                    miniRpcProtocol.setHeader(msgHeader);
                    miniRpcProtocol.setBody(rpcRequest);
                    out.add(miniRpcProtocol);
                }
                break;
            case RESPONSE:
                log.info(">>> decode response");
                MiniRpcResponse rpcResponse = rpcSerialization.deserialize(data, MiniRpcResponse.class);
                if(null != rpcResponse){
                    MiniRpcProtocol<MiniRpcResponse> miniRpcProtocol = new MiniRpcProtocol<>();
                    miniRpcProtocol.setHeader(msgHeader);
                    miniRpcProtocol.setBody(rpcResponse);
                    out.add(miniRpcProtocol);
                }
                break;
            case HEARTBEAT:
                // 处理 心跳检测 消息
                break;
            default:
                break;
        }

    }

}
